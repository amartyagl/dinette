import axios from "axios";
import React from "react";
import { useState } from "react";
import { useEffect } from "react";
import "../Style/style.css";
import "../Style/Menu_page.css"
import { CategoryList } from "./CategoryList";
import {Base_Url} from '../Components/BaseUrl'

function MenuPage() {
    const [foodItems, setFoodItems] = useState([]);

    useEffect(() => {
        document.querySelector('.footer-container').classList.add('d-none-menu')
        return () => {
            document.querySelector('.footer-container').classList.remove('d-none-menu')
        }
    }, [])


    // const API_URL = 'http://localhost:4500/foodItem';

     //const API_URL="http://localhost:9001/v1/api/menu";

    const API_URL = `${Base_Url}/menu-service/v1/api/menu`

    useEffect(() => {
         axios.get(API_URL,{ headers: {
            "Content-Type": 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization':  localStorage.getItem('token') 
          }})
            .then((response) => {
                setFoodItems(response.data);
            })
    }, []);

    return (
        <>
            <div className="menu_page">

                <section >
                    <div>
                        <CategoryList foodItems={foodItems} />
                    </div>

                </section>

            </div>

        </>

    )

}
export default MenuPage;