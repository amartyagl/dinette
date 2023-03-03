import "../Style/Login.css";
import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Base_Url } from '../Components/BaseUrl';
import axios from 'axios';

function Login(props) {
    const url = `${Base_Url}/authentication-service/v1/api/signIn`;
    const getURL = `${Base_Url}/user-service/api/v1/getUser/`;

    // React States
    const [errorMessages, setErrorMessages] = useState({});
    const [userNotFound, setuserNotFound] = useState("");
    const [formData, setFormData] = useState({
        email: "",
        password: ""
    })
    const [eye, seteye] = useState(true);
    const [password, setpassword] = useState("password");

    const navigate = useNavigate();

    const errors = {
        email: "Invalid email",
        password: "Invalid password"
    };

    const changeHandler = e => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    }

    const Eye = () => {
        if (password === "password") {
            setpassword("text");
            seteye(false);
        }
        else {
            setpassword("password");
            seteye(true);
        }
    }

    const submitHandler = e => {
        e.preventDefault();
        if (!formData.email) {
            setErrorMessages({ name: "email", message: errors.email });
        } else if (!formData.password) {
            setErrorMessages({ name: "password", message: errors.password });
        }
        else {
            axios.post(url, JSON.stringify(formData),
                {
                    headers: { 'Content-Type': 'application/json' },
                })
                .then(res => {
                    if (res.data) {
                        localStorage.setItem('token', (res.data));
                        axios.get(getURL + formData.email, {
                            headers: {
                                'Content-Type': 'application/json',
                                'Authorization': localStorage.getItem('token')
                            }
                        }
                        ).then(res => {
                            console.log("getdata", res);
                            if (res.status == 200) {
                                localStorage.setItem('userDetails', JSON.stringify(res.data));
                                navigate('/home');
                                props.sendLoginResponse("true");
                            }
                        })
                            .catch(err => toast.error('Something went wrong'))
                    }
                }


                )
                .catch(err => toast.error('Something went wrong'))

        }
    }

    // Generate JSX code for error message
    const renderErrorMessage = (name) =>
        name === errorMessages.name && (
            <div className="error"><p className="errorMsg">{errorMessages.message}</p></div>
        );

    return (
        <>
            <div className="loginPage">
                <div id="loginform">
                    <h2 id="headerTitle">Sign In</h2>
                    <form onSubmit={submitHandler}>
                        <div className="row row-data">
                            <label>email</label>
                            <div className="text-center">
                                <input type="text" placeholder="Enter your username" name="email" value={formData.email} onChange={changeHandler} /> <span className="formFieldIcon"><i className="fa fa-envelope"></i></span>
                            </div>
                            {renderErrorMessage("email")}
                        </div>
                        <div className="row row-data">
                            <label>Password</label>

                            <div className="text-center">
                                <input type={password} placeholder="Enter your password" name="password" value={formData.password} onChange={changeHandler} /> <span className="formFieldIcon cursor-pointer"><i onClick={Eye} className={`fa ${eye ? "fa-eye-slash" : "fa-eye"}`}></i></span>
                            </div>
                            {renderErrorMessage("password")}
                        </div>
                        <div id="button" className="row row-data mb-0">
                            <button>Log in</button>
                        </div>
                    </form>

                    <div className="noUser">
                        <p>{userNotFound}</p>
                    </div>

                    <div id="noAccount">
                        <label>Dont have an account? <Link to="/signup" className="linkTo">Sign Up Here</Link></label>
                    </div>
                </div>
            </div>

        </>
    )
}

export default Login;