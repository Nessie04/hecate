export interface LoginRequest {
  email: string;
  password: string;
}

export interface UserRequest {
  fullName: string;
  email: string;
  password: string;
  phone?: string;
  profilePicture?: string;
}

export interface UserResponse {
  userId: number;
  fullName: string;
  email: string;
  phone: string;
  profilePicture: string;
  reservations: ReservationResponse[];
}

export interface AuthResponse {
  token: string;
  userId: number;
  fullName: string;
  email: string;
  role: string;
}

export interface ReservationRequest {
  serviceName: string;
  dateTime: string;
}

export interface ReservationResponse {
  reservationId: number;
  userId: number;
  dateTime: string;
  serviceName: string;
  status: string;
}
