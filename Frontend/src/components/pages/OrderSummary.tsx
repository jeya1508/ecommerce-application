import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import '../styles/OrderSummary.css';
import Header from './Header.tsx';
import Footer from './Footer.tsx';

interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
}

interface Order {
    userId: number;
    productId: number;
    orderedDate: string;
    deliveryDate: string;
}

const OrderSummary: React.FC = () => {
    const { productId } = useParams<{ productId: string }>();
    const token = useSelector((state: any) => state.tokenLoader.token);
    const userId = useSelector((state: any) => state.tokenLoader.id);
    const [product, setProduct] = useState<Product | null>(null);
    const [order, setOrder] = useState<Order | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(false);

    useEffect(() => {
        const fetchProduct = async () => {
            try {
                     const response = await axios.get(`http://localhost:9876/product/products/${productId}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                setProduct(response.data);
            } catch (err) {
                console.error('Error fetching product details:', err);
            }
        };

        fetchProduct();
    }, [productId, token]);

    const handleConfirmPayment = async () => {
        setIsLoading(true);
        try {
            const response = await axios.post(
                `http://localhost:9876/orders/${userId}/${productId}`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );
            setOrder(response.data);
        } catch (err) {
            console.error('Error confirming payment:', err);
        } finally {
            setIsLoading(false);
        }
    };
    

    if (!product) {
        return <div>Loading...</div>;
    }

    return (
        <div className="order-summary-container">
            <Header/>
            <h2>Order Summary</h2>
            <div className="order-details">
                <p><h3>Product Name:</h3> {product.name}</p>
                <p><h3>Price:</h3> Rs. {product.price}</p>
                <p><h3>Description</h3>{product.description}</p>
            </div>
            {!order ? (
                <button className="confirm-payment-button" onClick={handleConfirmPayment} disabled={isLoading}>
                    {isLoading ? 'Loading...' : 'Confirm Payment'}
                </button>
            ) : (
                <div className="order-details">
                    <h3>Order Details</h3>
                    <p>Ordered Date: {order.orderedDate}</p>
                    <p>Delivery Date: {order.deliveryDate}</p>
                </div>
            )}
            <Footer/>
        </div>
    );
};

export default OrderSummary;
