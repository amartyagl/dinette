import { useNavigate } from "react-router-dom";
import "../Style/TimeTableInfo.css"

function TimeTableInfo() {

    const navigate = useNavigate();

    const navigateToPackage = () => {
        navigate("/packages")
    }


    return (
        <div className=" mt-5 TimeTableInfo-container">
            <div className="row">
                <div className="col-4 p-4 ColumnOne text-center ">
                    <h1>
                        Don't have time to cook your meal daily ?
                    </h1>
                    <span>
                        <h1 className="TimeTableArrow mt-5" > Look Here <i class="bi bi-arrow-right" style={{ "width": "inherit" }}></i></h1>
                    </span>
                </div>
                <div className="col-8 p-4">
                    <h1 className="text-center">Don't worry, we will do it for you</h1>
                    <div className="row p-3" >
                        <div className="col-12">

                            {/* <!-- Button trigger modal --> */}
                            <button type="button" class="btn btn-modal btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                <h1>Click here to know more </h1>
                            </button>

                            {/* <!-- Modal --> */}
                            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content modal-wholeBody">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel">Time Table Feature</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <ul className="my-2">
                                                <li className="modal-ele">Schedule your order.</li>
                                                <li className="modal-ele">No need to place order everyday.</li>
                                                <li className="modal-ele">Enter your order once and we will deliver it to you at your specific time</li>
                                                <li className="modal-ele">Choose a subcription package and get unlimited benefits.</li>
                                                <li className="modal-ele">We make fresh food daily.</li>
                                            </ul>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-primary" data-bs-dismiss="modal" onClick={navigateToPackage}>Explore Packages</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    )
};

export default TimeTableInfo;
