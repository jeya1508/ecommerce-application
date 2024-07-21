import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import '../styles/Header.css';
import { useDispatch } from 'react-redux';

import { setSavedToken } from '../../feature/redux/slice/tokenSlice.ts';

const Header: React.FC = () => {
    const isLoggedIn = useSelector((state: any) => state.tokenLoader.token !== '');
    const role = useSelector((state: any) => state.tokenLoader.role);
    const dispatch = useDispatch();
    const [isDropdownOpen, setIsDropdownOpen] = useState(false); // State for dropdown visibility

    const toggleDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    };

    function handleLogout(event): void {
        dispatch(setSavedToken({ token: '', id:'', role:null }));
    }

    return (
        <header className="header">
            {isLoggedIn?(
                <div className="header-left">
                <Link to="/home" className="app-name">SmartBuy</Link>
            </div> 
            ):(
                <div className="header-left">
                <Link to="/" className="app-name">SmartBuy</Link>
            </div>
            )}
            
            {isLoggedIn ? (
                <div className="header-right">
                    {role === 'ADMIN' && (
                        <Link to="/add-product" className="header-button">Add Product</Link>
                    )}
                    <Link to="/cart" className="header-button">Cart</Link>
                    <div className="dropdown">
                        <button className="header-button" onClick={toggleDropdown}>
                            Profile
                        </button>
                        {isDropdownOpen && (
                            <div className="dropdown-content">
                                <Link to="/profile" className="dropdown-item">Edit Profile</Link>
                                <Link to="/orders" className="dropdown-item">My Orders</Link>
                                <Link to="/login" className='dropdown-item' onClick={handleLogout} >Logout</Link>
                            </div>
                        )}
                    </div>
                </div>
            ) : (
                <div className="header-right">
                </div>
            )}
        </header>
    );
};

export default Header;


