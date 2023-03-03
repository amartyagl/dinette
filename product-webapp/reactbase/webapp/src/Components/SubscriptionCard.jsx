import { useNavigate } from "react-router-dom"
import "../Style/SubscriptionCard.css"
import Button from "./Button.jsx"


function SubscriptionCard() {

    const naviaget = useNavigate();

    const navigateToPackages = () => {
        naviaget('/packages')
    }

    return (
        <div className=" my-5 subscription-container">
            <div className="row">
                <div className="col-4 p-4 ColumnOne text-center ">
                    <h1>
                        Want some exciting offers, Cashbacks and Crazy deals ?
                    </h1>
                    <span>
                        <h1 className="TimeTableArrow mt-5" > Look Here <i class="bi bi-arrow-right" style={{ "width": "inherit" }}></i></h1>
                    </span>
                </div>
                <div className="col-8 p-4">
                    <h1 className="text-center">
                        Subscribe to one of our packages according to your need, And get unlimited benefits on every order.
                    </h1>
                    <div className="row p-2" >
                        <div className="col-12 btn-subs--container mt-3">
                            <button className="btn-subs" onClick={navigateToPackages} ><h1>Click here to explore</h1></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
};

export default SubscriptionCard;