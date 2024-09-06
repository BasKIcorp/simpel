import './app.css'
import './index.css';
import AuthPage from "./pages/auth/AuthPage";
import {InstallationChoice} from "./pages/installation/InstallationChoice";
import {DeviceParams} from "./pages/deviceParams/DeviceParams";
import {AdditionalOptions} from "./pages/additionalOptions/AdditionalOptions";
import {Navigate, Route, Routes} from "react-router-dom";
import RegPage from "./pages/registration/RegPage";
import {useSelector} from "react-redux";
import SelectionResults from "./pages/selection_results/SelectionResults";
import Result from "./pages/result/Result";

function App() {

    const token = useSelector((state) => state.token);

    const isAuthenticated = token !== "";

    return (

        <Routes>
            <Route path={"/"}>
                <Route path={'auth'}
                       element={!isAuthenticated ? <AuthPage/> : <Navigate to={"/selection/installation_choice"}/>}/>

                <Route path={"registration"} element={!isAuthenticated ? <RegPage/> : <Navigate to={"/installation_choice"}/>}/>
                <Route path={'/selection/'}>
                    <Route path={"installation_choice"}
                           element={isAuthenticated ? <InstallationChoice/> : <Navigate to={"/auth"}/>}/>
                    <Route path={"device_params"}
                           element={isAuthenticated ? <DeviceParams/> : <Navigate to={"/auth"}/>}/>
                    <Route path={"selection_results"}
                           element={isAuthenticated ? <SelectionResults/> : <Navigate to={"/auth"}/>}/>
                    <Route path={"additional_options"}
                           element={isAuthenticated ? <AdditionalOptions/> : <Navigate to={"/auth"}/>}/>
                    <Route path={"result"}
                           element={isAuthenticated ? <Result/> : <Navigate to={"/auth"}/>}/>
                </Route>
            </Route>
        </Routes>

    )
        ;
}

export default App;
