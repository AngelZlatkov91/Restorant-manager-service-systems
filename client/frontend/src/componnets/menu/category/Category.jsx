import { useEffect, useState } from "react";
import { useDeleteCategory, useGetAllCategory } from "../../../hooks/useCategory";
import { useNavigate } from "react-router-dom";
import ConfirmPopup from "../../confirmModal/ConfirmPop";

export default function Category() {
  const navigate = useNavigate();
  const [categories, refreshCategories] = useGetAllCategory();
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const [categoryToDelete, setCategoryToDelete] = useState(null);

  useEffect(() => {
    refreshCategories(); 
  }, []);

  const createCategoryHandler = () => {
    navigate("/createCategory");
  };

  const handleDeleteClick = (category) => {
    setCategoryToDelete(category);
    setIsConfirmOpen(true);
  };

  const handleConfirm = async () => {
    if (!categoryToDelete) return;
    await useDeleteCategory(categoryToDelete.id);
    await refreshCategories(); 
    setIsConfirmOpen(false);
    setCategoryToDelete(null);
  };

  const handleCancel = () => {
    setIsConfirmOpen(false);
    setCategoryToDelete(null);
  };

  const handleEdit = (id) => {
    navigate(`/editCategory/${id}`);
  };

  return (
    <section className="category-page">
      <header className="category-header">
        <h2>Категории</h2>
        <button className="btn btn-primary" onClick={createCategoryHandler}>
          ➕ Създай категория
        </button>
      </header>

      {categories && categories.length > 0 ? (
        <ul className="category-list">
          {categories.map((cat) => (
            <li key={cat.id} className="category-card">
              <span>{cat.category}</span>
              <div className="category-actions">
                <button className="btn btn-success" onClick={() => handleEdit(cat.id)}>
                  ✏️
                </button>
                <button className="btn btn-danger" onClick={() => handleDeleteClick(cat)}>
                  🗑
                </button>
              </div>
            </li>
          ))}
        </ul>
      ) : (
        <p className="empty-state">Няма налични категории.</p>
      )}

      <ConfirmPopup
        isOpen={isConfirmOpen}
        message={`Сигурен ли си, че искаш да изтриеш "${categoryToDelete?.category}"?`}
        onConfirm={handleConfirm}
        onCancel={handleCancel}
      />
    </section>
  );
}
