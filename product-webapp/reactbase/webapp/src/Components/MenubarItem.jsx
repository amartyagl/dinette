import "../Style/MenubarItem.css"
import { Link } from "react-router-dom";


function MenubarItem(props) {
    return (
        <div className="col-lg-2 container-menu--item">
            <Link to={props.path} className="menu-item" href="#">{props.MenuName}</Link>
        </div>
    )
};

export default MenubarItem;