import {Header} from "../UI/Header";
import classes from "./page.module.css"
export const PageTemplate = () => {
  return(
    <div>
        <Header/>
        <div className={classes.rectangle}>
            <div className={classes.inner}>
            </div>
        </div>
    </div>
  );
}