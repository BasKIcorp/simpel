import classes from "./header.module.css";
import simpel_logo from '../../assets/img.png'
import {useNavigate} from "react-router-dom";
import { useSelector } from "react-redux";
export const Header = () => {
    const token = useSelector((state) => state.user.token);
    const isAuthenticated = token !== "";
    const role = useSelector((state) => state.user.role);
    const isAdmin = role === "ADMIN";
    const navigate = useNavigate();
    const handleClick = () => {
       if (isAuthenticated){
           if (isAdmin){
               console.log("click log");
               navigate('/admin');
           }
       }
    };

    return (
            <div className={classes.header}>
                <img src={simpel_logo} alt={'simpel_logo'} className={classes.logo} onClick={handleClick}/>
            </div>
    );
}