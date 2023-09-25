import Routes from "./routes/routes";
import ErrorBoundry from "./error/ErrorBoundry";
import { ToastProvider } from 'react-toast-notifications';


function App() {
  return (
    <ToastProvider autoDismiss={true} autoDismissTimeout={5000}>
    <ErrorBoundry>
      <div className="App">
        <Routes />
      </div>
    </ErrorBoundry>
    </ToastProvider>
  );
}

export default App;
