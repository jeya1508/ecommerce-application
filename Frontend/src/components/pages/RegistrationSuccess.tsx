import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/RegistrationSuccess.css';
import Header from './Header.tsx';
import Footer from './Footer.tsx';


const RegistrationSuccess = () => {
    return (
        <div className="registration-success-container">
            <Header/>
        <div className="registration-success-box">
            <h2>Registration Successful!</h2>
            <p>Your account has been successfully registered.</p>
            <Link to="/login" className="login-button">Login</Link>
        </div>
        <Footer/>
    </div>
    );
};

export default RegistrationSuccess;
