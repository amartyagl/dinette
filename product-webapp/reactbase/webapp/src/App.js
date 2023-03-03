import Homepage from "./Homepage";
import { Routes, Route, } from "react-router-dom";
import Navbar from "./Components/Navbar";
import Footer from "./Components/Footer";
import Packagepage from "./Packagepage";
import Contactus from "./Contactus";
import Aboutus from "./Aboutus";
import Login from "./Components/Login"
import Signup from "./Components/SignUp"
import { Profile } from "./Components/Profile/Profile"
import Cart from "./Components/Cart";

import MenuCard from "./Time-Table/MenuCard";
import MenuPage from "./Components/MenuPage";
import Payment from "./Components/Payment";
import { ToastContainer, toast } from 'react-toastify';
  import 'react-toastify/dist/ReactToastify.css';
import { useEffect, useState } from "react";

function App() {
const [checkLoggedVar, setLoggedVar] = useState();

  const sendLoginResponse = (loggedVar) => {
    setLoggedVar(loggedVar);
  }
  return (
    <>
    <ToastContainer   />
      <Navbar checkLoggedVar={checkLoggedVar}
      />
      {/* <Homepage /> */}
      <Routes>
        <Route path="" element={<Homepage />}></Route>
        <Route path="home" element={<Homepage />}></Route>
        <Route path="menu" element={<MenuPage />}></Route>
        <Route path="packages" element={<Packagepage />}></Route>
        <Route path="about" element={<Aboutus />}></Route>
        <Route path="contact" element={<Contactus />}></Route>
        <Route path="/login" element={<Login sendLoginResponse={sendLoginResponse} />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/time-table" element={<MenuCard />} />
        <Route path="/payment" element={<Payment />} />
      </Routes>
      <Footer />
    </>
  )
}
export default App;