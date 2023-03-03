import Logo from "./Logo";
import Menubar from "./Menubar";
import LoginButton from "./LoginButton";
import '../Style/Navbar.css'


function Navbar(props) {
    return (
        <div className="container-fluid container-navbar sticky-top">
            <div className="row-navbar d-flex text-center align-items-center">
                <div className="container-menuitem col-lg-2">
                    <Logo />
                </div>
                <div className="container-menuitem col-lg-8">
                    <Menubar />
                </div>
                <div className="container-menuitem col-lg-2">
                    <LoginButton loginVar = {props.checkLoggedVar} />
                </div>
            </div>
        </div>
    )
};

export default Navbar;