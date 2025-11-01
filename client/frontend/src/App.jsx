import { Route, Routes } from "react-router-dom";
import Header from "./componnets/header/Header";
import Login from "./componnets/user/login/Login";
import Register from "./componnets/user/register/Register";
import Menu from "./componnets/menu/Menu";
import CreateCategory from "./componnets/menu/category/CreateCategory";
import Category from "./componnets/menu/category/Category";
import CreateItem from "./componnets/menu/items/createItem/CreateItem";
import Item from "./componnets/menu/items/Item";
import EditItem from "./componnets/menu/items/editeItem/EditItem";



function App() {
 

  return (
   <>

       <Header />
       <Routes>
         <Route path="/login" element={<Login />} />
         <Route path="/register" element={<Register />} />
         <Route path="/menu" element={<Menu />} />
         <Route path="/createCategory" element={<CreateCategory />} />
         <Route path="/getAllCategory" element={<Category />} />
         <Route path="/createItem" element={<CreateItem />} />
         <Route path="/getAll-items" element={<Item />} />
         <Route path="/editItem/:id" element={<EditItem />} />
       </Routes>
      
   </>
  );
}

export default App;
