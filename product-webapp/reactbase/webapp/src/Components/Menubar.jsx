import MenubarItem from "./MenubarItem";
import '../Style/Menubar.css'
import { Route, Routes } from "react-router-dom";

function Menubar() {
        return (
                <div className=" container-menubar d-flex justify-content-evenly align-items-center">
                        {/* <Routes> */}
                        <MenubarItem MenuName="Home" path="home" />
                        <MenubarItem MenuName="Packages" path="packages" />
                        <MenubarItem MenuName="Menu" path="menu" />
                        <MenubarItem MenuName="About Us" path="about" />
                        <MenubarItem MenuName="Contact US" path="contact" />
                        {/* </Routes> */}
                </div>
        )
};

export default Menubar;