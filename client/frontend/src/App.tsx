import { Routes, Route } from 'react-router-dom';


import Login from './componnets/login/Login';
import Header from './componnets/header/Header';
import Register from './componnets/register/Register';

function App() {


  return (
    <div>
      <Header />
      <Routes>
        <Route path='/login' element={<Login />} />
        <Route path='/register' element={<Register />} />
      </Routes>
      
    </div>
  );
}

export default App;


  
  


