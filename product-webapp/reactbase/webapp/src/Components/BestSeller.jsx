import React from "react";
import FoodCard from "./FoodCard";

function BestSeller({ foodItems }) {
    foodItems.sort((a,b)=>b.id-a.id);
    foodItems=foodItems.slice(0,3);
    return (
        <section className="best-seller-container">
            <div >
                <FoodCard list={foodItems} />
            </div>
        </section>
    )

}
export default BestSeller;