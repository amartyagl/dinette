import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import "../Style/LogoutOption.css"
import Button from "./Button.jsx";
import { useEffect } from "react";


function LoginButton(props) {
let user = JSON.parse(localStorage.getItem ('userDetails'));
  useEffect(()=>{
    console.log(props.loginVar);
    (localStorage.getItem('token') || props.loginVar) ? isLoggedIn(true) : isLoggedIn(false)

}, [props.loginVar])

    let [loggedIn, isLoggedIn] = useState(false)
    const navigate = useNavigate()

    

    const navigateToLoginPage = (e) => {
        navigate('/Login')
        e.preventDefault();
    }

    const naviagteToHome = () => {
        localStorage.clear();
        isLoggedIn(false)
        navigate('/home')
        window.location.reload(false);
    }


    // axios.get("http://localhost:5000/cart").then(res => setTotalItem(res.data.length))


    // increase the number of item on click

    if (loggedIn) {
        return (
            <div className="d-flex justify-content-evenly align-item-center">
                <div className="d-flex justify-content-evenly align-item-end">
                    <h1><Link to="/cart" ><i className="bi bi-cart2 "></i></Link></h1>
                    {/* <h4>{totalItem}</h4> */}
                </div>
                <div className="d-flex justify-content-center align-item-center">
                    <div className="dropdown">
                        <i className=" bi bi-person-circle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                        </i>
                        <div className="dropdown-menu container p-4" style={{
                            backgroundColor: "#FFF5E4", width: "300px", height: "300px",
                        }}>
                            <div className="row p-3">
                                <div className="col-12 text-left" style={{ color: "#850E35" }}><h4>Welcome {user.name}</h4></div>
                                <hr />
                                <div className="col-12 my-3"><h5 ><Link to="/Profile" className="user--profile">Profile</Link></h5></div>
                                <div className="col-12 my-3">
                                    <h5 ><Link to="/time-table" className="user--timetable">Schedule your order</Link></h5>
                                </div>
                                <div className="col-12 mt-3">
                                    <h5><Button navigateTo={naviagteToHome} ButtonName="Logout" /></h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    else return (

        <div className="d-flex justify-content-evenly align-item-center">
            <div className="d-flex justify-content-evenly align-item-end">
                <h1><Link to="/cart" ><i className="bi bi-cart2 "></i></Link></h1>
                {/* <h4>{totalItem}</h4> */}
            </div>
            <h1><a href="" onClick={navigateToLoginPage} style={{ color: "#850E35" }} ><i className="bi bi-box-arrow-in-right"></i></a></h1>
        </div>
    )
};

export default LoginButton;