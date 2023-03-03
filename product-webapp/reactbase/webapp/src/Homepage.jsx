import Carousal from "./Components/Carousal.jsx";
import FoodCategory from "./Components/FoodCategory.jsx";
import './Style/Homepage.css'
import PopularItems from "./Components/PopularItems.jsx";
import Feedback from "./Components/Feedback.jsx";
import TimeTableInfo from "./Components/TimeTableInfo.jsx"
import SubscriptionCard from "./Components/SubscriptionCard.jsx";

function Homepage() {

    return (
        <div>
            <Carousal />
            <hr />
            <FoodCategory />
            <TimeTableInfo />
            <PopularItems />
            <hr />
            <SubscriptionCard />
            <Feedback />
        </div>
    )
};

export default Homepage;