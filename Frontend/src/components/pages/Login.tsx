import React, { useState } from 'react';
import '../styles/Login.css';
import { useDispatch } from 'react-redux';
import { setSavedToken } from '../../feature/redux/slice/tokenSlice.ts';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import Header from './Header.tsx';
import Footer from './Footer.tsx';

const Login = () => {
    const [formData, setFormData] = useState({ email: '', password: '' });
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:9876/user/login', formData);
            const authResponse = response.data;
            console.log(authResponse);
            dispatch(setSavedToken({ token: authResponse.token, id: authResponse.userId, role: authResponse.role }));
          
            navigate("/home");
        } catch (err) {
            setError('Invalid credentials'); 
        }
    };

    return (
        <div className="login-container">
            <Header/>
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <label>Email</label>
                <input type="email" name="email" value={formData.email} onChange={handleChange} required />
                <label>Password</label>
                <input type="password" name="password" value={formData.password} onChange={handleChange} required />
                {error && <p className="error">{error}</p>}
                <button type="submit">Login</button>
            </form>
            <div>
                <p className='new-account'>Don't have an account? <Link to="/register">Register here</Link></p>
            </div>
            <Footer/>
        </div>
    );
};
export default Login;