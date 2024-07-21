import './App.css';
import { BrowserRouter as Router,Route,Routes } from 'react-router-dom';
import Login from './components/pages/Login.tsx';
import Register from './components/pages/Register.tsx';
import { Provider } from "react-redux";
import RegistrationSuccess from './components/pages/RegistrationSuccess.tsx';
import store from './app/store.ts';
import Home from './components/pages/Home.tsx';
import LandingPage from './components/pages/LandingPage.tsx';
import ProductDetail from './components/pages/ProductDetails.tsx';
import AddProduct from './components/pages/AddProduct.tsx';
import Cart from './components/pages/Cart.tsx';
import OrderSummary from './components/pages/OrderSummary.tsx';
import OrderHistory from './components/pages/OrderHistory.tsx';
import ProfileEdit from './components/pages/ProfileEdit.tsx';



function App() {
  return (
      <Provider store = {store}>
      <Router>
            <Routes>
                <Route path="/register" element = {<Register/>} />
                <Route path="/login" element = {<Login/>} />
                <Route path="/" element = {<LandingPage/>} />
                <Route path="/home" element={<Home />} />
                <Route path='/add-product' element={<AddProduct/>}/>
                <Route path='/cart' element={<Cart/>}/>
                <Route path="/product/:id" element={<ProductDetail/>} />
                <Route path='/order-summary/:productId' element={<OrderSummary/>} />
                <Route path="/registration-success" element = {<RegistrationSuccess/>} />
                <Route path='/orders' element={<OrderHistory/>}/>
                <Route path='/profile' element = {<ProfileEdit/>} />
            </Routes>
        </Router>
        </Provider>
    
  );
}

export default App;
