import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/Footer.css'; // Import your footer CSS file

const Footer: React.FC = () => {
    return (
        <footer className="footer">
            <div className="footer-left">
            </div>
            <div className="footer-right">
                <p className="copyright">Copyrights 2024</p>
            </div>
        </footer>
    );
};


export default Footer;