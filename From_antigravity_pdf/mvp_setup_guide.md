# E-Commerce Platform — MVP Foundation Setup Guide

## Project Structure (created)

```
Java_Ecommerce/
├── pom.xml
├── .gitignore
└── src/main/
    ├── java/com/ecommerce/
    │   ├── EcommerceApplication.java
    │   ├── config/
    │   │   ├── SecurityConfig.java
    │   │   └── DataSeeder.java
    │   ├── controller/
    │   │   ├── HomeController.java
    │   │   ├── AuthController.java
    │   │   ├── AdminController.java
    │   │   ├── SellerController.java
    │   │   └── BuyerController.java
    │   ├── dto/
    │   │   └── UserRegistrationDto.java
    │   ├── exception/
    │   │   └── ResourceNotFoundException.java
    │   ├── model/
    │   │   ├── Role.java, OrderStatus.java
    │   │   ├── User.java, Category.java, Product.java
    │   │   ├── Order.java, OrderItem.java
    │   │   ├── Wishlist.java, BrowsingHistory.java
    │   │   └── ActivityLog.java
    │   ├── repository/   (8 interfaces, one per entity)
    │   └── service/
    │       ├── UserService.java
    │       └── CustomUserDetailsService.java
    └── resources/
        ├── application.properties
        └── templates/
            ├── layout.html
            ├── home.html, login.html, register.html
            ├── admin/dashboard.html
            ├── seller/dashboard.html
            └── buyer/dashboard.html
```

---

## Step 1 — Create the MySQL Database

Open a MySQL client (MySQL Workbench, terminal, etc.) and run:

```sql
CREATE DATABASE IF NOT EXISTS ecommerce_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

---

## Step 2 — Set Environment Variables

**PowerShell (current session):**
```powershell
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "yourpassword"
```

**Or system-wide (permanent):**
```powershell
[System.Environment]::SetEnvironmentVariable("DB_USERNAME", "root", "User")
[System.Environment]::SetEnvironmentVariable("DB_PASSWORD", "yourpassword", "User")
```

> Replace `root` / `yourpassword` with your actual MySQL credentials.

---

## Step 3 — Build & Run

```powershell
cd "c:\Users\Shubhang Pandey\OneDrive\Pictures\Desktop\Java_Ecommerce"
.\mvnw.cmd spring-boot:run
```

If you don't have the Maven wrapper, use your installed Maven:
```powershell
mvn spring-boot:run
```

---

## Step 4 — Verify It Works

Open **http://localhost:8080** in your browser.

| What you should see | URL |
|---|---|
| Public home page with Login / Register buttons | `http://localhost:8080/` |
| Login form | `http://localhost:8080/login` |
| Registration form (Buyer / Seller radio buttons) | `http://localhost:8080/register` |

---

## Step 5 — Test Seeded Accounts

The `DataSeeder` inserts these accounts on first run (skips if data already exists):

| Role | Email | Password | Redirects to |
|---|---|---|---|
| **Admin** | `admin@store.com` | `admin123` | `/admin/dashboard` |
| **Seller** | `seller@store.com` | `seller123` | `/seller/dashboard` |
| **Buyer** | `buyer@store.com` | `buyer123` | `/buyer/dashboard` |

Each dashboard shows placeholder cards describing upcoming modules. The navbar adapts per role.

> [!IMPORTANT]
> The seller account also seeds **3 products** (Wireless Headphones, Cotton T-Shirt, Clean Code) across 3 categories (Electronics, Clothing, Books). You can verify in MySQL:
> ```sql
> USE ecommerce_db;
> SELECT * FROM users;
> SELECT * FROM products;
> SELECT * FROM categories;
> ```

---

## Step 6 — First Git Commit

```bash
cd "c:\Users\Shubhang Pandey\OneDrive\Pictures\Desktop\Java_Ecommerce"
git init
git add .
git commit -m "feat: MVP foundation — entities, repos, security, seed data, Thymeleaf layout"
```

---

## Security Rules Summary

| URL Pattern | Access |
|---|---|
| `/`, `/login`, `/register`, `/products`, `/css/**`, `/js/**` | Public |
| `/admin/**` | `ROLE_ADMIN` only |
| `/seller/**` | `ROLE_SELLER` only |
| `/buyer/**` | `ROLE_BUYER` only |
| Everything else | Authenticated |

Admin accounts cannot be self-registered — the registration form only offers Buyer / Seller.

---

## What's NOT Built Yet (next steps, on your go)

- Product listing / detail pages
- Cart and checkout
- Order management
- Dashboard data and analytics
- Image uploads
