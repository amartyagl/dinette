import "../Style/Footer.css"


function Footer() {
    return (
        <div className="footer-container mt-5">
            <div className="mt-4 d-flex justify-content-around">
                <div>
                    <ul>
                        <h4>Usefull Links:</h4>
                        <br /><br />
                        <li>Refund Policy</li>
                        <li>Terms and Conditions</li>
                        <li>Privacy Policy</li>
                        <li>Coupon Policy</li>
                    </ul>
                </div>
                <div>
                    <ul>
                        <h4>Acceptable Payment Methods:</h4>
                        <br /><br />
                        <li>VISA</li>
                        <li>GPay</li>
                        <li>PayTM</li>
                        <li>MasterCard</li>
                    </ul>
                </div>
                <div>
                    <h4>@Connect with us:</h4>
                    <br /><br />
                    <h4 className="d-flex justify-content-between">
                        <i class="bi bi-facebook"></i>
                        <i class="bi bi-instagram"></i>
                        <i class="bi bi-twitter"></i>
                    </h4>
                    <div className="mt-3">
                        <span><i class="bi bi-envelope"></i> : </span>
                        <span>info@dinette.com</span>
                    </div>
                    <div className="mt-1">
                        <span><i class="bi bi-telephone"></i> : </span>
                        <span>9999900000</span>
                    </div>
                </div>
            </div>
            <hr />
            <div className="text-center">

                <span>Copyright. All Right Reserved</span>
            </div>
        </div>
    )
};

export default Footer;