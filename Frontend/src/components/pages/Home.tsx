import axios from "axios";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import '../styles/Home.css';
import { FaSearch } from "react-icons/fa";
import { Link } from "react-router-dom";
import Header from "./Header.tsx";
import Footer from "./Footer.tsx";

interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
}

const Home: React.FC = () => {
    const role = useSelector((state: any) => state.tokenLoader.role);
    const token = useSelector((state: any) => state.tokenLoader.token);
    const [products, setProducts] = useState<Product[]>([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [error, setError] = useState<string | null>(null);

    const fetchProducts = async () => {
        try {
            const response = await axios.get('http://localhost:9876/product/products/all', {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            setProducts(response.data);
            setError(null);
        } catch (err) {
            console.error('Error fetching products:', err);
            setError('Failed to fetch products. Please try again later.');
        }
    };

    useEffect(() => {
        fetchProducts();
    }, [token]);

    const handleSearch = async () => {
        try {
            if (searchTerm.trim() === '') {
                fetchProducts(); 
                return;
            }

            const response = await axios.get(`http://localhost:9876/product/search/${searchTerm}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            // Set products from the response data
            setProducts(response.data);
            setError(null); // Clear previous errors
        } catch (err) {
            console.error('Error searching for product:', err);
            setProducts([]); // Clear products if search fails
            setError('Failed to search products. Please try again later.');
        }
    };

    return (
        <div className="home-container">
            <Header />
            <h1>{role === 'ADMIN' ? 'Welcome Admin' : 'Welcome to the Application'}</h1>
            <div className="search-container">
                <input
                    type="text"
                    placeholder="Search products..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="search-box"
                />
                <button className="searchIcon" onClick={handleSearch}><FaSearch /></button>
            </div>
            <div className="products-container">
                {error && <p className="error-message">{error}</p>}
                {products.length > 0 ? (
                    products.map(product => (
                        <div key={product.id} className="product-block">
                            <h3>{product.name}</h3>
                            <p>Rs. {product.price}</p>
                            <p>{product.description}</p>
                            <Link to={`/product/${product.id}`}>
                                <button className="view-details-button">View Details</button>
                            </Link>
                        </div>
                    ))
                ) : (
                    <p>No products found</p>
                )}
            </div>
            <Footer />
        </div>
    );
};

export default Home;
