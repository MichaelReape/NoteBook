const Modal = ({
  onClose,
  children,
}: {
  onClose: () => void;
  children: React.ReactNode;
}) => {
  return (
    <div>
      <button onClick={onClose}>X</button>
      {children}
    </div>
  );
};

export default Modal;
