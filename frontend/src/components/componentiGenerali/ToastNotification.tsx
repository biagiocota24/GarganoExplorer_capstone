import { Toast, ToastContainer } from "react-bootstrap";
import { FiCheckCircle, FiAlertCircle, FiInfo } from "react-icons/fi";

type ToastType = "success" | "error" | "info";

interface ToastNotificationProps {
  show: boolean;
  type: ToastType;
  title: string;
  message: string;
  onClose: () => void;
}

const iconMap = {
  success: FiCheckCircle,
  error: FiAlertCircle,
  info: FiInfo,
};

const bgMap: Record<ToastType, string> = {
  success: "success",
  error: "danger",
  info: "info",
};

const ToastNotification = ({ show, type, title, message, onClose }: ToastNotificationProps) => {
  const Icon = iconMap[type];

  return (
    <ToastContainer position="top-end" className="p-3" style={{ zIndex: 9999, position: "fixed" }}>
      <Toast show={show} onClose={onClose} delay={4000} autohide bg={bgMap[type]}>
        <Toast.Header>
          <Icon className="me-2" size={16} />
          <strong className="me-auto">{title}</strong>
        </Toast.Header>
        <Toast.Body className="text-white fw-semibold">{message}</Toast.Body>
      </Toast>
    </ToastContainer>
  );
};

export default ToastNotification;
