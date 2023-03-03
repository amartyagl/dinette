import { useState } from "react";
import FoodCard from "./FoodCard";

function MenuSection({foodItems}){
    return(<div>
        <section className="menu-container">
            
            <div >
                <FoodCard list={foodItems} />
            </div>
        </section>
        </div>

    )
}
export default MenuSection;