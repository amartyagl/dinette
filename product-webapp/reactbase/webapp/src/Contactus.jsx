import "./ContactUs.css";

function Contactus() {
    return (
        <div className="container-fluid">
            <h2 className="contactUs_header">Feel free to get in touch with us...</h2>
            <div className="row d-flex">
                <div className="col-lg-6">
                    <div className="card contactUs_card">
                        <div className="card-head">
                            <div className="card-title">
                                <h5 className="contactUs_title">Send us a Message</h5>
                            </div>
                        </div>
                        <div className="card-body">
                            <form className="row g-3">
                                <div className="col-6 fieldBox">
                                    <label className="form-label">Name</label>
                                    <input type="text" className="form-control" placeholder="Enter your name" />
                                </div>
                                <div className="col-6 fieldBox">
                                    <label className="form-label">Email</label>
                                    <input type="email" className="form-control" placeholder="Enter your email" />
                                </div>
                                <div className="col-6 fieldBox">
                                    <label className="form-label">Contact Number</label>
                                    <input type="text" className="form-control" placeholder="Enter your contact number" />
                                </div>
                                <div className="col-6 fieldBox">
                                    <label className="form-label">Message</label>
                                    <textarea type="text" className="form-control" placeholder="Type here..." rows="1" />
                                </div>
                                <div className="col-12 sendBtn">
                                    <button type="submit" className="btn btn-primary">Send</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div className="col-lg-6 ">
                    <div className="card connectUs_card">
                        <div className="card-head">
                            <div className="card-title">
                                <h5 className="contactUs_title">Get in touch </h5>
                            </div>
                        </div>
                        <div className="card-body">
                            <div className="row">
                                <div className="col-4">
                                    <div className="connectUs_label">
                                        <span><i class="bi bi-envelope"></i> : </span>
                                        <span> info@dinette.com</span>
                                    </div>
                                    <div className="mt-2 connectUs_label">
                                        <span><i class="bi bi-telephone"></i> : </span>
                                        <span> 9999900000</span>
                                    </div>
                                    <div className="socialIcon connectUs_label">
                                        <p>Follow us:</p>
                                        <h4 className="d-flex  justify-content-between">
                                            <i class="bi bi-facebook cursor-pointer"></i>
                                            <i class="bi bi-instagram cursor-pointer"></i>
                                            <i class="bi bi-twitter cursor-pointer"></i>
                                        </h4>
                                        {/* <div className="d-flex">
                                            <i class="fa fa-facebook"></i>
                                            <i class="fa fa-instagram"></i>
                                            <i class="fa fa-twitter"></i>
                                        </div> */}
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

export default Contactus;