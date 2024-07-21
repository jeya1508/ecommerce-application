import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useSelector } from 'react-redux';
import '../styles/Cart.css';
import Header from './Header.tsx';
import Footer from './Footer.tsx';

interface CartItem {
    id: number;
    productId: number;
    productDetails?: {
        name: string;
        price: number;
        description: string;
    };
}

const Cart: React.FC = () => {
    const [cartItems, setCartItems] = useState<CartItem[]>([]);
    const [productDetails, setProductDetails] = useState<any[]>([]); // Separate state for product details
    const [error, setError] = useState<string>('');
    const [loading, setLoading] = useState<boolean>(false);

    const token = useSelector((state: any) => state.tokenLoader.token);
    const userId = useSelector((state: any) => state.tokenLoader.id);

    useEffect(() => {
        const fetchCartItems = async () => {
            try {
                const response = await axios.get(`http://localhost:9876/cart/${userId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setCartItems(response.data);
                setError('');
            } catch (error) {
                if (error.response && error.response.data) {
                    setError(error.response.data);
                } else {
                    setError('An error occurred. Please try again later.');
                }
            }
        };

        if (token && userId) {
            fetchCartItems();
        }
    }, [token, userId]);

    useEffect(() => {
        const fetchProductDetails = async () => {
            try {
                const productIds = cartItems.map((item) => item.productId);
                const requests = productIds.map((id) =>
                    axios.get(`http://localhost:9876/product/products/${id}`, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    })
                );
                const responses = await Promise.all(requests);
                setProductDetails(responses.map(response => response.data)); // Store product details separately
            } catch (error) {
                console.error('Error fetching product details:', error);
            }
        };

        if (cartItems.length > 0) {
            fetchProductDetails();
        }
    }, [cartItems, token]);

    const handleRemoveFromCart = async (productId: number) => {
        setLoading(true);
        try {
            await axios.delete(`http://localhost:9876/cart/${userId}/${productId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });
            const updatedCartItems = cartItems.filter((item) => item.productId !== productId);
            setCartItems(updatedCartItems);
            setLoading(false);
        } catch (error) {
            console.error('Error removing item from cart:', error);
            if (error.response && error.response.data) {
                setError(error.response.data);
            } else {
                setError('An error occurred while removing the item. Please try again.');
            }
        }
    };

    return (
        <div>
            <Header />
            <div className="cart-container">
                <h1>Cart Items</h1>
                {loading ? (
                    <p>Loading...</p>
                ) : cartItems.length > 0 ? (
                    <div className="cart-grid">
                        {cartItems.map((item, index) => (
                            <div key={item.id} className="cart-item">
                                <p>Product Name: {productDetails[index]?.name}</p>
                                <p>Price: Rs. {productDetails[index]?.price}</p>
                                <p>Description: {productDetails[index]?.description}</p>
                                <button onClick={() => handleRemoveFromCart(item.productId)}>Remove</button>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p>No items in cart.</p>
                )}
                {error && <p className="error">{error}</p>}
            </div>
            <Footer />
        </div>
    );
};

export default Cart;
