import axios from 'axios';
import * as React from 'react';
import { useEffect, useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Base_Url } from '../Components/BaseUrl'
export default function FoodCard({ list }) {

  // const [cartData, setCartData] = useState([]);

  // const API_URL = 'localhost:68087/v1/api/cart';

  // useEffect(() => {
  //   axios.get(API_URL)
  //     .then((response) => {
  //       setCartData(response.data);
  //     })
  // }, []);

  const addItem = ["data"];

  const userDatail = localStorage.getItem('userDetails')
  const email = localStorage.getItem('userDetails') ? JSON.parse(userDatail).email : ""

  function onCartAdditionClick(item) {

    addItem.splice(0, 1, item);
    const cartDetails =
    {
      "cartId": 1,
      "quantity": 1,
      "value": 1,
      "emailId": email,
      "food": [addItem[0]]
    }

    axios.get(`${Base_Url}/order-service/v1/api/cart`, {
      headers: {
        "Content-Type": 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Authorization': localStorage.getItem('token')
      }
    }).then(res => {
      let arrayOfFoodId = [];
      res.data.map((el) => {
        arrayOfFoodId.push(el.food[0].foodItemId)
      })
      let cartDataId = addItem[0].foodItemId
      if (arrayOfFoodId.includes(cartDataId)) {
        toast.error(`Item already in Cart`)
      }
      else {
        axios.post(`${Base_Url}/order-service/v1/api/addCart`, cartDetails, {
          headers: {
            "Content-Type": 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization': localStorage.getItem('token')
          }
        })
        toast.success(` ${item.foodName} added in your Cart`)

      }
    })


    //===========================================================================

    //   axios.post("localhost:8087/v1/api/addCart", ...addItem).then(res => {console.log(res)
    //     toast.success(` ${item.foodName} added in your Cart`)
    // }).catch(err => console.log(err))
    // axios.post("localhost:8087/v1/api/addCart", ...addItem, { headers: {
    //   "Content-Type": 'application/json',
    //   'Access-Control-Allow-Origin': '*',
    //   'Authorization':  localStorage.getItem('token') 
    // }}).then(res => console.log(res)).catch(err => console.log(err))

  }
  // useEffect(() => {
  //   list.forEach((ele) => {
  //     ele.quantity = 1;
  //   })
  // }, [list])

  // async function onCartAdditionClick(data) {
  //   const previousMap = await localStorage.getItem('cartData') && JSON.parse(localStorage.getItem('cartData'))
  //   let objectToParse = { ...previousMap }
  //   let id = data.id
  //   if (objectToParse[id]) {
  //     objectToParse[id].quantity += 1
  //   }
  //   else {
  //     objectToParse[id] = data
  //   }
  //   localStorage.setItem('cartData', JSON.stringify(objectToParse))
  //   // console.log("Inside-->",objectToParse)

  //   // console.log("Inside",objectToParse)
  //   onSend(objectToParse)
  // }


  // function onSend(map) {
  //   const keys = Object.keys(map);
  //   // let cartData=[];
  //   keys.forEach((element,index) => {
  //     if(cartData[index]?.id.includes(element)){
  //       console.log(cartData);
  //       console.log(element);
  //     //  cartData.push(map[element])
  //     cartData[index].quantity +=1;
  //     }
  //     else{
  //       cartData.push(map[element])
  //     }

  //   })
  //   console.log(keys)
  //   // axios.post('localhost:8087/v1/api/addCart',{data:arr})
  //   // console.log(arr);
  //   // arr.forEach((item)=>{
  //   // cartData.push(item)})

  // }



  // function onCartAdditionClick(data){
  //   const listToParse = [...cartItems]
  // 
  //=======================================================================================
  return (
    <div className={`list-wrapper`}>
      {list && list?.map((food) => {
        return (
          <div className={`food-box ${list?.length === 1 ? 'w-250' : ''}`} key={food.id}>
            <div className="img-wrapper">
              <img className="food-image" src={food.foodPicture} alt={food.foodName} />
            </div>
            <div className="food-name">{food.foodName}</div>

            <div className="food-description">{food.foodDescription}</div>
            <div className="button-container">
              <div className='buy-now'><div className="food-price">₹ {food.foodSize.small}</div></div>
              <button className='add-cart' onClick={() => { onCartAdditionClick(food) }}>
                <div className="menu-button">ADD TO CART</div>
              </button>
            </div>
          </div>
        )
      })}
    </div>
  );
}
