import Button from "./Button.jsx";
import { useState } from "react";
import axios from "axios";
import { Base_Url } from './BaseUrl.jsx'


function CartComponent(props) {

    let [totalCost, setTotalCost] = useState(props.foodPrice)
    let [num, setNum] = useState(1);

    // getting price after increasing the quantity 
    const totalPrice = (quantity) => {
        setTotalCost(props.foodPrice * quantity)
    }

    // get data after deleting the item and sending back to cart component
    const getData = () => {
        axios.get(`${Base_Url}/order-service/v1/api/cart`, {
            headers: {
                "Content-Type": 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': localStorage.getItem('token')
            }
        }).then(
            res => {
                props.getDataAfterDeleteItem(res.data)
            }
        )
    }

    // delete items in cart

    const deletItemCart = (id) => {
        axios.delete(`${Base_Url}/order-service/v1/api/cart/${props.cartId}`, {
            headers: {
                "Content-Type": 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': localStorage.getItem('token')
            }
        }).then(() => getData()).catch(err => console.log(err))
    }
    // change the quantity quantity
    let incNum = () => {
        if (num < 10) {
            setNum(num + 1);
            // return num +1 for increamented value
            totalPrice(num + 1)
            props.incPrice(props.foodPrice, num)
        }
    };

    let decNum = () => {
        if (num > 1) {
            setNum(num - 1);
            totalPrice(num - 1)
            props.decPrice(props.foodPrice, num)
        }
    }
    let handleChange = (e) => {
        setNum(e.target.value);
    }

    return (
        <div className="row m-4">
            <div class="card" style={{ "backgroundColor": "rgba(238, 105, 131,0.3)" }}>
                <div class="card-header">
                    <h5>Item</h5>
                </div>
                <div class="card-body">
                    <div className="row">
                        <div className="col-3">
                            <h5>Title</h5>
                            <p>{props.foodName}</p>
                        </div>
                        <div className="col-4" style={{ "textAlign": "-webkit-center" }}>
                            <h5>Quantity</h5>
                            <div className="input-group" style={{ "width": "30%" }}>
                                <a className="btn btn-outline-primary" onClick={decNum}>-</a>
                                <input type="text" className="form-control text-center" value={num} onChange={handleChange} style={{ "backgroundColor": "#FFF5E4", "border": "1px solid #850E35 " }} />
                                <a className="btn btn-outline-primary" onClick={incNum}>+</a>
                            </div>
                        </div>
                        <div className="col-4 text-center">
                            <h5>Total</h5>
                            <p>₹ {totalCost}</p>
                        </div>
                        <div className="col-1 d-flex justify-content-center">
                            <Button ButtonName="Remove" navigateTo={() => deletItemCart(props.cartId)} />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
};

export default CartComponent;