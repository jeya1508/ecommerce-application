import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, Link } from 'react-router-dom';
import { useSelector } from 'react-redux';
import '../styles/ProductDetail.css';
import Header from './Header.tsx';
import Footer from './Footer.tsx';

interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
}

const ProductDetail: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const token = useSelector((state: any) => state.tokenLoader.token);
    const userId = useSelector((state: any) => state.tokenLoader.id);
    const [product, setProduct] = useState<Product | null>(null);
    const [message, setMessage] = useState<string>('');

    useEffect(() => {
        const fetchProduct = async () => {
            try {
                const response = await axios.get(`http://localhost:9876/product/products/${id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setProduct(response.data);
            } catch (err) {
                console.error('Error fetching product details:', err);
            }
        };

        fetchProduct();
    }, [id, token]);

    const handleAddToCart = async () => {
        try {
            await axios.post(
                'http://localhost:9876/cart/add',
                { userId, productId: product?.id },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                }
            );
            setMessage('Product added to cart successfully!');
        } catch (err) {
            console.error('Error adding product to cart:', err);
            setMessage('Failed to add product to cart.');
        }
    };

    if (!product) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <Header/>
        <div className="product-detail-container">
            
            <h2>{product.name}</h2>
            <p>{product.description}</p>
            <p>Rs. {product.price}</p>
            <button onClick={handleAddToCart} className = "product-details-button">Add to Cart</button>
            <Link to={`/order-summary/${product.id}`}>
                <button className = "product-details-button">Buy Now</button>
            </Link>
            {message && <p className='message-text'>{message}</p>}
        </div>
        <Footer/>
        </div>
    );
};

export default ProductDetail;
