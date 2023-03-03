import { useEffect, useState } from "react";
import axios from "axios";
import SampleImage from "./sample-image.jpg"
import Button from "./Button.jsx"
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Base_Url } from './BaseUrl.jsx'

function PopularItems() {

    const [data, setData] = useState([])

    let cartData = ["cartData"]

    let userEmail = JSON.parse(localStorage.getItem('userDetails'))

    const getMenuData = () => {
        axios.get(`${Base_Url}/menu-service/v1/api/menu`, {
            headers: {
                "Content-Type": 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': localStorage.getItem('token')
            }
        }).then((res) => { setData(res.data); console.log(res.data) })
    }

    useEffect(() => getMenuData(), []);

    const addItemToCart = (addedToCart) => {
        cartData.splice(0, 1, addedToCart)
        const cartDetails =
        {
            "quantity": 1,
            "value": 1,
            "emailId": userEmail.email,
            "food": [cartData[0]]
        }

        axios.get(`${Base_Url}>/order-service/v1/api/cart`, {
            headers: {
                "Content-Type": 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': localStorage.getItem('token')
            }
        }).then(res => {
            let arrayOfFoodId = []
            res.data.map(el => {
                arrayOfFoodId.push(el.food[0].foodItemId)
            })
            let cartDataId = cartData[0].foodItemId
            if (arrayOfFoodId.includes(cartDataId)) {
                toast.success(`item already added to cart`)
            }
            else {
                axios.post(`${Base_Url}/order-service/v1/api/addCart`, cartDetails, {
                    headers: {
                        "Content-Type": 'application/json',
                        'Access-Control-Allow-Origin': '*',
                        'Authorization': localStorage.getItem('token')
                    }
                })
                toast.success(` item added`)
            }
        })
    }
    let popularItem = data.slice(0, 5).map((item) => {
        return < div className="card m-4" style={{ width: "19rem", backgroundColor: 'rgba(255, 196, 196,0.5)', overflow: "hidden" }}>
            <img src={item.foodPicture} className="card-img-top" alt="..." style={{ transform: "scale(1.2)", objectFit: "contain" }} />
            <div className="card-body mt-4">
                <h4 className="card-title">{item.foodName}</h4>
                <h5 className="card-text"> ₹{item.foodSize.small}</h5>
                <div className="text-end ">
                    <Button navigateTo={() => addItemToCart(item)} ButtonName="Add to Cart" />
                </div>
            </div >
        </div >
    });
    return (
        <>
            <h2 className="text-center mt-5">Best Sellers</h2>
            <div className="d-flex justify-content-evenly my-4">
                {popularItem}
            </div>

        </>

    )
};
export default PopularItems;