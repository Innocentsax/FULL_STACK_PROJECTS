import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Landing, Error, Register, ProtectedRoute, Layout, ForgotPassword, ResetPassword, ReceiptSummary, PrintReceipt } from './pages';
import {
  Customers, 
  Dashboard, 
  AccountSettings, 
  Orders,
  SharedLayout,
  AddCustomer, 
  AllOrders
} from './pages/dashboard';


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path='/'
          element={
            <ProtectedRoute>
              <SharedLayout />
            </ProtectedRoute>
          }
        >
          <Route index element={<Dashboard />} />
          <Route path='customers' element={<Customers />} />
          <Route path='add-customers' element={<AddCustomer />} />
          <Route path='orders' element={<Orders />} />
          <Route path='all-orders' element={<AllOrders />} />
          <Route path='account-settings' element={<AccountSettings />} />
          <Route path='orders-receipt/:id' element={<ReceiptSummary />} />
        </Route>
        <Route element={<Layout />}>
          <Route path='register' element={<Register />} />
          <Route path='forgot-password' element={<ForgotPassword />} />
          <Route path='reset-password' element={<ResetPassword />} />
          {/* <Route path='register' element={<ForgotPassword />} /> */}
        </Route>
        <Route path='landing' element={<Landing />} />
        <Route path='print-receipt/:id' element={<PrintReceipt />} />
        <Route path='*' element={<Error />} />
      </Routes>
      <ToastContainer position='top-center' />
    </BrowserRouter>
  );
}

export default App;
