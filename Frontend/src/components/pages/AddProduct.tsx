// AddProduct.tsx

import React, { useState } from 'react';
import axios from 'axios';
import { useSelector } from 'react-redux';
import '../styles/AddProduct.css';
import Header from './Header.tsx';
import Footer from './Footer.tsx';

const AddProduct: React.FC = () => {
    const token = useSelector((state: any) => state.tokenLoader.token);

    const [name, setName] = useState('');
    const [seller, setSeller] = useState('');
    const [description, setDescription] = useState('');
    const [price, setPrice] = useState('');
    const [stockCount, setStockCount] = useState('');
    const [review, setReview] = useState<string[]>([]);
    const [newReview, setNewReview] = useState('');
    const [message, setMessage] = useState('');

    const handleAddReview = () => {
        if (newReview.trim() !== '') {
            setReview([...review, newReview]);
            setNewReview('');
        }
    };

    const handleRemoveReview = (index: number) => {
        const updatedReviews = review.filter((_, i) => i !== index);
        setReview(updatedReviews);
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:9876/product/products/add', {
                name,
                seller,
                description,
                price: parseFloat(price),
                stockCount: parseInt(stockCount),
                review
            }, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.status === 200) {
                setMessage('Product added successfully!');
                setName('');
                setSeller('');
                setDescription('');
                setPrice('');
                setStockCount('');
                setReview([]);
            }
        } catch (err) {
            setMessage('Error adding product.');
        }
    };

    return (
        <div className="add-product-container">
            <Header/>
            <h2>Add Product</h2>
            {message && <p className="message">{message}</p>}
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Product Name:</label>
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Seller:</label>
                    <input
                        type="text"
                        value={seller}
                        onChange={(e) => setSeller(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Description:</label>
                    <input
                        type="text"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Price:</label>
                    <input
                        type="number"
                        value={price}
                        onChange={(e) => setPrice(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Stock Count:</label>
                    <input
                        type="number"
                        value={stockCount}
                        onChange={(e) => setStockCount(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Reviews:</label>
                    <input
                        type="text"
                        value={newReview}
                        onChange={(e) => setNewReview(e.target.value)}
                    />
                    <button type="button" onClick={handleAddReview} className="add-review-button">
                        Add Review
                    </button>
                    <ul className="reviews-list">
                        {review.map((review, index) => (
                            <li key={index} className="review-item">
                                {review}
                                <button type="button" onClick={() => handleRemoveReview(index)} className="remove-review-button">
                                    Remove
                                </button>
                            </li>
                        ))}
                    </ul>
                </div>
                <button type="submit" className="submit-button">Submit</button>
            </form>
            <Footer/>
        </div>
       
    );
};
export default AddProduct;
