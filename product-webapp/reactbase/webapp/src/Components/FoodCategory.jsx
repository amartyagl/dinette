import FoodCategoryItem from "./FoodCategoryItem";
import Button from "./Button";
import { useNavigate } from "react-router-dom";
import SampleImage from "./sample-image.jpg"
import sampleImage2 from "./sample-image2.jpg"
import veg from '../Img/veg.jpeg'
import sampleImage3 from "./sample-image3.jpg"
import sampleImage4 from "./sample-image4.jpg"
import sampleImage5 from "./sample-image5.jpg"
function MenuCaterogy() {

    const navigate = useNavigate()
    const navigateToMenu = () => navigate('/menu')

    return (
        <div style={{ backgroundColor: 'rgba(255, 196, 196,0.5)' }} >
            <h2 className="text-center">Food Category</h2>
            <div className="mt-4 d-flex justify-content-evenly">
                <FoodCategoryItem items="Veg Items" src={SampleImage} />
                <FoodCategoryItem items="Non-Veg Items" src={sampleImage2} />
                <FoodCategoryItem items="Daily Combo" src={sampleImage3} />
                <FoodCategoryItem items="Dessert" src={sampleImage4} />
                <FoodCategoryItem items="Beverages" src={sampleImage5} />
            </div>
            <div className="text-center mt-4">
                <Button navigateTo={navigateToMenu} ButtonName="Explore Menu" />
            </div>
        </div>
    )
}

export default MenuCaterogy;