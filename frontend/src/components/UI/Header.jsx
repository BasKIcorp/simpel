import classes from "./header.module.css";
import simpel_logo from '../../assets/img.png'
export const Header = () => {
    return (
            <div className={classes.header}>
                <img src={simpel_logo} alt={'simpel_logo'} className={classes.logo}/>
            </div>
    );
}