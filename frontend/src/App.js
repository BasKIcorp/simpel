import './app.css'
import './index.css';
import AuthPage from "./pages/auth/AuthPage";
import {InstallationChoice} from "./pages/installation/InstallationChoice";
import {DeviceParams} from "./pages/deviceParams/DeviceParams";
import {AdditionalOptions} from "./pages/additionalOptions/AdditionalOptions";
function App() {
  return (
      <div>
        {/*<AuthPage/>*/}
          <AdditionalOptions/>
      </div>

  );
}

export default App;
