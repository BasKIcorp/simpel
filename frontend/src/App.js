import './app.css';
import './index.css';
import AuthPage from "./pages/auth/AuthPage";
import { InstallationChoice } from "./pages/installation/InstallationChoice";
import {DeviceParamsHydra} from "./pages/deviceParams/DeviceParamsHydra";
import { AdditionalOptions } from "./pages/additionalOptions/AdditionalOptions";
import { Navigate, Route, Routes } from "react-router-dom";
import RegPage from "./pages/registration/RegPage";
import { useSelector } from "react-redux";
import SelectionResults from "./pages/selection_results/SelectionResults";
import Result from "./pages/result/Result";
import {DeviceParamsHVS} from "./pages/deviceParams/DeviceParamsHVS";
import {DeviceParamsPNSVPV} from "./pages/deviceParams/DeviceParamsPNSVPV";
import {DeviceParamsPNSAUTP} from "./pages/deviceParams/DeviceParamsPNSAUTP";

function App() {
    const token = useSelector((state) => state.token);
    const isAuthenticated = token !== "";

    return (
        <Routes>
            {/* Корневой маршрут */}
            <Route path="/" element={isAuthenticated ? <Navigate to="/selection/installation_choice" /> : <Navigate to="/auth" />} />

            {/* Другие маршруты */}
            <Route path="auth" element={!isAuthenticated ? <AuthPage /> : <Navigate to="/selection/installation_choice" />} />
            <Route path="registration" element={!isAuthenticated ? <RegPage /> : <Navigate to="/selection/installation_choice" />} />
            <Route path="/selection">
                <Route path="installation_choice" element={isAuthenticated ? <InstallationChoice /> : <Navigate to="/auth" />} />
                <Route path={"device_params_hydromodule"} element={isAuthenticated ? <DeviceParamsHydra/> : <Navigate to="/auth" />}/>
                <Route path={"device_params_hvs"} element={isAuthenticated ? <DeviceParamsHVS/> : <Navigate to="/auth" />}/>
                <Route path={"device_params_pns_vpv"} element={isAuthenticated ? <DeviceParamsPNSVPV/> : <Navigate to="/auth" />}/>
                <Route path={"device_params_pns_autp"} element={isAuthenticated ? <DeviceParamsPNSAUTP/> : <Navigate to="/auth" />}/>
                <Route path="selection_results" element={isAuthenticated ? <SelectionResults /> : <Navigate to="/auth" />} />
                <Route path="additional_options" element={isAuthenticated ? <AdditionalOptions /> : <Navigate to="/auth" />} />
                <Route path="result" element={isAuthenticated ? <Result /> : <Navigate to="/auth" />} />
            </Route>
        </Routes>
    );
}

export default App;