import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/LandingPage.css';
import Header from './Header.tsx';
import Footer from './Footer.tsx';

const LandingPage = () => {
    return (
      <div className="landing-page">
        <Header/>
        <div className="landing-content">
          <div className="text-content">
            <h1>Welcome to SmartBuy App</h1>
            <p>Discover a world of shopping right at your fingertips. Explore products from various categories.</p>
            <div className="action-buttons">
              <Link to="/login" className="login-button">Login</Link>
              <Link to ="/register" className="register-button">Register</Link>
            </div>
          </div>
        </div>
        <Footer/>
      </div>
    );
  }
  

export default LandingPage;
