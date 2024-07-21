import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useSelector } from 'react-redux';
import '../styles/OrderHistory.css';
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
    product?: Product; // Make product optional
}

const OrderHistory: React.FC = () => {
    const token = useSelector((state: any) => state.tokenLoader.token);
    const userId = useSelector((state: any) => state.tokenLoader.id);
    const [orders, setOrders] = useState<Order[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const orderResponse = await axios.get(`http://localhost:9876/orders/${userId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                const ordersData = orderResponse.data;
                
                const productRequests = ordersData.map((order: Order) =>
                    axios.get(`http://localhost:9876/product/products/${order.productId}`, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    })
                );

                const productResponses = await Promise.all(productRequests);
                const ordersWithProducts = ordersData.map((order: Order, index: number) => ({
                    ...order,
                    product: productResponses[index].data,
                }));

                setOrders(ordersWithProducts);
            } catch (err) {
                console.error('Error fetching order history:', err);
            } finally {
                setIsLoading(false);
            }
        };

        fetchOrders();
    }, [userId, token]);

    if (isLoading) {
        return <div>Loading...</div>;
    }

    if (orders.length === 0) {
        return <div>No orders found.</div>;
    }

    return (
        <div>
            <Header/>
            <h2 className='order-heading'>Order History</h2>
            <div className="order-grid">
                {orders.map((order) => (
                    <div key={order.productId} className="order-block">
                        <h3>{order.product?.name}</h3>
                        <p>{order.product?.description}</p>
                        <p>Price: Rs. {order.product?.price}</p>
                        <p>Ordered Date: {order.orderedDate}</p>
                        <p>Delivery Date: {order.deliveryDate}</p>
                    </div>
                ))}
            </div>
        <Footer/>
        </div>
    );
};

export default OrderHistory;
