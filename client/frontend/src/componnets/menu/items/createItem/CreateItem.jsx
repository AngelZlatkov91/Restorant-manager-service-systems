
const initialValues = {
  name: '',
  price: '',
  category: '',
  typeProduct: '',
}

export default function CreateItem() {
      
   return (
     
        <>
          <section id="create-page" className="auth">
            <form id="create" onSubmit={submitHandler}>
                <div className="container">

                    <h1>Create Game</h1>
                    <label htmlFor="leg-title">Item Name:</label>
                    <input 
                    type="text" 
                    id="name" 
                    name="name" 
                    value={values.name}
                    onChange={changeHandler}
                    placeholder="Enter item name..." />

                    <label htmlFor="price">Price:</label>
                    <input 
                    type="number" 
                    id="price" 
                    name="price"
                    value={values.price}
                    onChange={changeHandler}
                     placeholder="Enter item price" />

                    <label htmlFor="levels">Category</label>
                    <input 
                    type="text" 
                    id="category" 
                    name="category"
                    value={values.category}
                    onChange={changeHandler} 
                     />

                    <label htmlFor="typeProduct">Type Product</label>
                    <input
                    type="text"
                    name="typeCategory" 
                    id="typeCategory"
                    value={values.typeCategory}
                    onChange={changeHandler}
                    />
                    <input className="btn submit" type="submit" value="Create Item" />
                </div>
            </form>
         </section>
       
       </>
       
    
  );
}