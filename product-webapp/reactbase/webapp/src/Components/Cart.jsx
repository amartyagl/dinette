import Button from "./Button";
import "../Style/Cart.css"
import { useEffect, useState } from "react";
import CartComponent from "./CartComponent";
import axios from "axios";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Base_Url } from './BaseUrl.jsx'
import Payment from './Payment.jsx'
import CartPayment from "./Cart-Payment";
import { useNavigate } from "react-router-dom";
function Cart() {

    let [cart, isCart] = useState([])
    let [instruction, isInstruction] = useState("")
    let [date, isDate] = useState("")
    let [time, isTime] = useState("")
    let [promo, isPromo] = useState("")
    let [discountPrice, isDiscountPrice] = useState("")
    let [priceToDisplay, isPriceToDisplay] = useState(0)
    let [openPayment, setOpenPayment] = useState(false)


    const navigate = useNavigate()
    let subTotalArr = []


    const coupons = {
        "FIRST100": 100,
        "FREEDEL": 30,
        "FREEDOM50": 50
    }

    const getCartdata = () => {
        axios.get(`${Base_Url}/order-service/v1/api/cart`, {
            headers: {
                "Content-Type": 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': localStorage.getItem('token')
            }
        }).then((res) => {
            isCart(res.data)
            let dataComing = res.data.map(el => el.food[0])
            dataComing.map((item) => { subTotalArr.push(item.foodSize.small) })
            isPriceToDisplay(subTotalArr.reduce((total, curr) => total + curr, 0))
        }).catch(err => console.log(err))
    }

    useEffect(() => getCartdata(), [])
    // getting cart data after deleting item 
    const getDataAfterDelete = (dataAfterDelete) => {
        isCart(dataAfterDelete);
        let dataComing = dataAfterDelete.map(el => el.food[0])
        dataComing.map((item) => { subTotalArr.push(item.foodSize.small) })
        isPriceToDisplay(subTotalArr.reduce((total, curr) => total + curr, 0))
    }

    // updating the price and display the total UI 

    const incPrice = (price, num) => {
        let subTotal = priceToDisplay + price;
        isPriceToDisplay(subTotal)
    }

    const decPrice = (price, num) => {
        let subTotal = priceToDisplay - price;
        isPriceToDisplay(subTotal)
    }

    const instructionChange = (e) => {
        isInstruction(e.target.value)
    }
    const instructionsHandler = (e) => {
        e.preventDefault()
        toast.success(`okay we will ${instruction}`)
    }

    const dateChange = (e) => {
        isDate(e.target.value)
    }
    const timeChange = (e) => {
        isTime(e.target.value)
    }
    const scheduleHandeler = () => {
        toast.success(`your order will be delivered on ${date} at ${time}`)
    }

    const promoHandler = (e) => {
        isPromo(e.target.value)
    }

    const couponHandler = () => {
        if (coupons.hasOwnProperty(promo)) {
            for (var i in coupons) {
                if (i === promo) {
                    isDiscountPrice(coupons[i])
                    toast.success(`Congratulations you get ₹${coupons[i]} off on your order`)
                }
            }
        }
        else (
            toast.error(`Sorry no such coupon available`)
        )
    }


    let foodArr = []
    let arr = []

    const foodObj = cart.map(el => {
        foodArr.push(...el.food)
    })

    foodArr.map(el => {
        arr.push({
            "foodName": el.foodName,
            "foodSize": el.foodSize.small,
            "foodPicture": el.foodPicture
        })
    })

    let localStorageData = JSON.parse(localStorage.getItem("userDetails"))

    let orderEmail = {
        "emailId": localStorageData.email,
        "firstName": localStorageData.name,
        foodlist: foodArr,
        value: priceToDisplay - discountPrice
    }

    const sendEmail = (id) => {
        axios.post(`${Base_Url}/notification-service/api/v1/sendOrderEmail`, {
            "emailId": localStorageData.email,
            "firstName": localStorageData.name,
            orderList: arr,
            orderValue: priceToDisplay - discountPrice,
            orderId: id
        }, {
            headers: {
                "Content-Type": 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': localStorage.getItem('token')
            }
        }).then(navigate('/profile'))
    }

    // setOpenPayment(true)
    // console.log(data, 'data ===>')

    const OrderStatus = () => {
        axios.post(`${Base_Url}/order-service/v1/api/add`, orderEmail, {
            headers: {
                "Content-Type": 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': localStorage.getItem('token')
            }
        }).then((res) => sendEmail(res.data.orderId)).catch(err => console.log(err))

    }

    const cartPayment = (data) => {
        setOpenPayment(true)
    }


    let ItemInCart = cart.map(el => {
        let food = el.food[0]
        return <CartComponent key={el.id} cartId={el.cartId} foodItemId={food.foodItemId} foodName={food.foodName} foodPrice={food.foodSize.small} id={food.cartId} foodAvailability={food.foodAvailability} foodDescription={food.foodDescription} getDataAfterDeleteItem={getDataAfterDelete} incPrice={incPrice} decPrice={decPrice} />
    })

    return (

        <>
            <div className="container-fluid cart-wrapper">
                <h2 className="text-center">Your Cart</h2>
                <div className="row m-4">
                    <div className="col-8 container-cartComponent" >
                        {ItemInCart}
                    </div>

                    {/* Offers and checkout  */}

                    <div className="col-4">
                        <div className="container-fluid container-promo">

                            {/* promo offer */}
                            <h5>Have a coupon ? <br /> Enter here : </h5>

                            <div className="row d-flex justify-content-between mt-3">
                                <div className="col-8 text-center" >
                                    <input type="text" className="p-3" onChange={promoHandler} placeholder="Enter Promo Code" style={{ "width": "100%", "height": "3rem" }}></input>
                                </div>
                                <div className="col-4 text-center" >
                                    <Button ButtonName="Submit" navigateTo={couponHandler} />
                                </div>
                            </div>

                            <hr />

                            <div className="row">
                                <h5>Want to schedule your order ?</h5>
                                <div className="col-8 d-flex justify-content-between mt-2" >
                                    <input type="date" className="mr-2" onChange={dateChange} style={{ "height": "3rem", "width": "48%" }}></input>
                                    <input type="time" onChange={timeChange} style={{ "height": "3rem", "width": "48%" }} />
                                </div>
                                <div className="col-4 text-center">
                                    <Button ButtonName="Submit" navigateTo={scheduleHandeler} />
                                </div>
                            </div>
                            <hr />
                            <div className="row d-flex justify-content-between mb-3">
                                <h5>Have some specific instructions for us ?</h5>
                                <div className="col-8 mt-3">
                                    <textarea id="freeform" name="freeform" rows="3" cols="35" placeholder="Let us know here" onChange={instructionChange}>
                                    </textarea>
                                </div>
                                <div className="col-4 mt-3 text-center">
                                    <Button ButtonName="Submit" navigateTo={instructionsHandler} />
                                </div>
                            </div>

                            <div className="row">
                                <div className="col-12">
                                    <div className="row d-flex p-1">
                                        <div className="col-6">
                                            SubTotal
                                        </div>
                                        <div className="col-6 text-end">
                                            ₹{priceToDisplay}
                                        </div>
                                    </div>
                                </div>
                                <div className="col-12">
                                    <div className="row d-flex p-1">
                                        <div className="col-6">
                                            Discount
                                        </div>
                                        <div className="col-6 text-end">
                                            {- discountPrice}
                                        </div>
                                    </div>
                                </div>
                                <div className="col-12">
                                    <div className="row d-flex p-1">
                                        <div className="col-6">
                                            <b><h5>To Pay</h5></b>
                                        </div>
                                        <div className="col-6 text-end">
                                            <b><h5>₹{priceToDisplay - discountPrice}</h5></b>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div className="row mt-5" style={{ "width": "75%", "margin": "auto" }}>
                                <Button ButtonName="Pay Now" navigateTo={() => { cartPayment(cart) }} />
                            </div>
                        </div>
                    </div>
                </div>
            </div >

            {
                openPayment &&
                <CartPayment open={openPayment} paymentSuccess={OrderStatus} cartAmount={orderEmail.value} close={() => { setOpenPayment(false) }} />
            }
        </>
    )
};

export default Cart;