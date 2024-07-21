import React, { useState } from 'react';
import axios from 'axios';
import { useSelector } from 'react-redux';
import '../styles/ProfileEdit.css';
import Header from './Header.tsx';
import Footer from './Footer.tsx';

const ProfileEdit: React.FC = () => {
    const token = useSelector((state: any) => state.tokenLoader.token);
    const userId = useSelector((state: any) => state.tokenLoader.id);

    const [name, setName] = useState<string>('');
    const [email, setEmail] = useState<string>('');
    const [phoneNumber, setPhoneNumber] = useState<string>('');

    const [message, setMessage] = useState<string>('');

    const handleFormSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        try {
            const response = await axios.put(
                'http://localhost:9876/profile/edit',
                {
                    name,
                    email,
                    phoneNumber,
                    userId
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                }
            );

            setMessage('Profile updated successfully!');
        } catch (error) {
            console.error('Error updating profile:', error);
            setMessage('Failed to update profile. Please try again.');
        }
    };

    return (
        <div>
            <Header/>
        <div className="profile-edit-container">
            <h2>Edit Profile</h2>
            <form onSubmit={handleFormSubmit}>
                <div className="form-group">
                    <label htmlFor="name">Name:</label>
                    <input
                        type="text"
                        id="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="phoneNumber">Phone Number:</label>
                    <input
                        type="tel"
                        id="phoneNumber"
                        value={phoneNumber}
                        onChange={(e) => setPhoneNumber(e.target.value)}
                        required
                    />
                </div>
                <button className='edit-profile-button' type="submit">Submit</button>
            </form>
            {message && <p className="message">{message}</p>}
        </div>
        <Footer/>
        </div>
    );
};

export default ProfileEdit;
