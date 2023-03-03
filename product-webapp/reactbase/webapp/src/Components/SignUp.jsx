import "../Style/SignUp.css";
import React, { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {Base_Url} from '../Components/BaseUrl'

function Signup() {
    // const url = 'http://localhost:8080/user-service/api/v1/addUser';
    const url = `${Base_Url}/user-service/api/v1/addUser`;
    const navigate = useNavigate();
    // React States
    const [formData, setFormData] = useState({
        name: "",
        mobileNumber: "",
        email: "",
        password: "",
    })
    const [formErrors, setFormErrors] = useState({});
    const [isSubmit, setIsSubmit] = useState(false);
    const [eye, seteye] = useState(true);
    const [password, setpassword] = useState("password");

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

    const changeHandler = e => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    }

    const submitHandler = e => {
        e.preventDefault();
        setFormErrors(validate(formData));
        setIsSubmit(true);
    }

    useEffect(() => {
        var dataToPost = {
            name: formData.name,
            email: formData.email,
            mobileNumber: formData.mobileNumber,
            password: formData.password
        }
        if (Object.keys(formErrors).length === 0 && isSubmit) {
            fetch(url, {
                method: 'POST',
                headers: {
                    "Content-Type": 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                body: JSON.stringify(dataToPost)
            })

                .then(response => {
                    console.log('response', response);
                    if (response.status === 201) {
                        toast.success('Registration successful');
                        navigate('/login');
                    }
                }).catch(e => {
                    console.log(e)
                    toast.error('Something went wrong')
                })
        }
    }, [formErrors]);

    const validate = (values) => {
        const errors = {};
        const email_regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i;
        const phone_regex = /^\d{10}$/;
        const password_regex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])([a-zA-Z0-9@$!%*?&]{8,})$/;

        if (!values.name) {
            errors.name = "Name is required!";
        }
        if (!values.email) {
            errors.email = "Email is required!";
        } else if (!email_regex.test(values.email)) {
            errors.email = "Kindly enter valid email address";
        }
        if (!values.mobileNumber) {
            errors.mobileNumber = "Mobile number is required!";
        } else if (!phone_regex.test(values.mobileNumber)) {
            errors.mobileNumber = "Kindly enter valid mobile number";
        }
        if (!password_regex.test(values.password)) {
            errors.password = "Password must contain at least one digit,one capial letter,one special character and size should be greater than 8"
        }
        if (!values.confirmPassword) {
            errors.confirmPassword = "Confirm Password is required";
        } else if (values.confirmPassword !== values.password) {
            errors.confirmPassword = "Password and confirm password should be same";
        }

        return errors;
    };

    return (
        <>
            <div className="signUpPage">
                <div id="signupForm">
                    <h2 id="title">Sign Up</h2>
                    <form onSubmit={submitHandler}>
                        <div className="row row-data">
                            <div className="col-6">
                                <label>Name</label>
                                <div className="text-center">
                                    <input type="text" placeholder="Enter your name" name="name" value={formData.name} onChange={changeHandler} /><span className="formFieldIcon"><i className="fa fa-user"></i></span>
                                </div>
                                <p className="reqMsg">{formErrors.name}</p>
                            </div>
                            <div className="col-6">
                                <label className="xpadding">Email</label>
                                <div className="text-center">
                                    <input type="text" placeholder="Enter your email" name="email" value={formData.email} onChange={changeHandler} /> <span className="formFieldIcon"><i className="fa fa-envelope"></i></span>
                                </div>
                                <p className="reqMsg">{formErrors.email}</p>
                            </div>
                            <div className="col-6">
                                <label>Contact No.</label>
                                <div className="text-center">
                                    <input type="text" placeholder="Enter your contact no." name="mobileNumber" value={formData.mobileNumber} onChange={changeHandler} /><span className="formFieldIcon"><i className="fa fa-phone"></i></span>
                                </div>
                                <p className="reqMsg">{formErrors.mobileNumber}</p>
                            </div>
                            <div className="col-6">
                                <label className="xpadding">Password</label>
                                <div className="text-center">
                                    <input type={password} placeholder="Enter your password" name="password" value={formData.password} onChange={changeHandler} /> <span className="formFieldIcon cursor-pointer"><i onClick={Eye} className={`fa ${eye ? "fa-eye-slash" : "fa-eye"}`}></i></span>
                                </div>
                                <p className="reqMsg">{formErrors.password}</p>
                            </div>
                            <div className="col-6">
                                <label>Confirm Password</label>
                                <div className="text-center">
                                    <input type="text" placeholder="Enter your confirm password" name="confirmPassword" value={formData.confirmPassword} onChange={changeHandler} />
                                </div>
                                <p className="reqMsg">{formErrors.confirmPassword}</p>
                            </div>
                            <div className="col-6">

                            </div>
                        </div>
                        <div id="signupBtn" className="mb-3" >
                            <button data-bs-toggle="modal" data-bs-target="#exampleModal1">Sign Up</button>
                        </div>
                    </form>
                    <div id="haveAccount">
                        <label>Already have an account? <Link to="/login" className="linkTo">Sign In here</Link></label>
                    </div>
                </div>
            </div>


            {/* <Alert
                header={''}
                btnText={'Close'}
                text={alert.text}
                type={alert.type}
                show={alert.show}
                onClosePress={onCloseAlert}
                pressCloseOnOutsideClick={true}
                showBorderBottom={true}
                alertStyles={{
                    background: '#fff5e4',
                    width: '20%',
                    position: 'absolute',
                    borderRadius: '12px',
                    top: '300px',
                    left: '700px',
                    textAlign: 'center',
                    paddingBottom:'10px'
                }}
                headerStyles={{}}
                textStyles={{ textAlign: 'center' }}
                buttonStyles={{ padding: '5px',
                    color: 'white',
                    borderRadius: '10px',
                    textDecoration: 'none',
                    fontSize: '15px'}}
            /> */}
        </>
    )

}
export default Signup;  