import { useGetAllCategory } from "../../../hooks/useCategory";

export default function Category() {
   const [category] = useGetAllCategory();
   
    

   return (
    <section id="category">
      <ul>
        {category && category.length > 0 ? (
          category.map((cat) => (
            <li key={cat.id}>
              <p>{cat.category}</p>
            </li>
          ))
        ) : (
          <p>Няма налични категории.</p>
        )}
      </ul>
    </section>
  );
}