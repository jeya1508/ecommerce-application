import React, { useState } from "react";
import '../styles/Register.css';
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import Header from "./Header.tsx";
import Footer from "./Footer.tsx";

const Register = () => {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        phoneNumber: '',
        password: '',
        confirmPassword: '',
    });

    const [errors, setErrors] = useState({});
    const [success, setSuccess] = useState('');

    const navigate = useNavigate();
    const validateField = (name, value) => {
        let error = '';
        switch (name) {
            case 'email':
                if (!/\S+@\S+\.\S+/.test(value)) {
                    error = 'Invalid email format';
                }
                break;
            case 'password':
                if (value.length < 8) {
                    error = 'Password must be at least 8 characters long';
                }
                break;
            case 'confirmPassword':
                if (value !== formData.password) {
                    error = 'Passwords do not match';
                }
                break;
            default:
                break;
        }
        setErrors({
            ...errors,
            [name]: error,
        });
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
        validateField(name, value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const newErrors = {};
        Object.keys(formData).forEach((key) => validateField(key, formData[key]));
        if (Object.values(errors).some((error) => error)) {
            return;
        }

        const { confirmPassword, ...submitData } = formData;

        try {
            const response = await axios.post('http://localhost:9876/user/register', submitData);
            setSuccess('Registration successful');
            setErrors({});
            navigate('/registration-success');
        } catch (err) {
            if (err.response && err.response.data && err.response.data.message) {
                const backendError = err.response.data.message;
                setErrors({
                    ...errors,
                    backendError,
                });
            } else {
                setErrors({
                    ...errors,
                    backendError: 'Registration failed',
                });
            }
            setSuccess('');
        }
    };

    return (
        <div className="register-container">
            <Header/>
            <div className="background-image"></div>
            <form className="register-form" onSubmit={handleSubmit}>
                <h2>Register</h2>
                {success && <p className="success">{success}</p>}
                <div className="input-group">
                    <label htmlFor="name">Name</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        required
                    />
                    {errors.name && <p className="error">{errors.name}</p>}
                </div>
                <div className="input-group">
                    <label htmlFor="email">Email</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                    />
                    {errors.email && <p className="error">{errors.email}</p>}
                </div>
                <div className="input-group">
                    <label htmlFor="phoneNumber">Phone Number</label>
                    <input
                        type="tel"
                        id="phoneNumber"
                        name="phoneNumber"
                        value={formData.phoneNumber}
                        onChange={handleChange}
                        required
                    />
                    {errors.phoneNumber && <p className="error">{errors.phoneNumber}</p>}
                </div>
                <div className="input-group">
                    <label htmlFor="password">Password</label>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                    {errors.password && <p className="error">{errors.password}</p>}
                </div>
                <div className="input-group">
                    <label htmlFor="confirmPassword">Confirm Password</label>
                    <input
                        type="password"
                        id="confirmPassword"
                        name="confirmPassword"
                        value={formData.confirmPassword}
                        onChange={handleChange}
                        required
                    />
                    {errors.confirmPassword && <p className="error">{errors.confirmPassword}</p>}
                </div>
                <button type="submit" className="register-button">Register</button>
                <div>
                <p className="account-present">Already have an account? <Link to="/login">Login</Link></p>
                </div>
                {errors.backendError && <p className="error">{errors.backendError}</p>}
            </form>
            <Footer/>
        </div>
    );
};

export default Register;