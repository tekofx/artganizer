export default interface AlertMessage {
  message: string;
  severity: "success" | "error" | "info" | "warning";
}
