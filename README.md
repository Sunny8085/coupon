Cart System with Coupons and Discounts
Overview

This system allows the application of discounts to specific products or the entire cart. Discounts are applied using coupon codes, with constraints such as minimum 
purchase requirements, usage limits, and product-specific discounts. The system supports both product-level and cart-level discounts, and handles various complex 
scenarios such as stacking discounts, quantity thresholds, and time-based coupon validity.
Use Cases

The following use cases cover a wide range of coupon and discount scenarios. Some have been implemented, while others are listed for future development due to time constraints or complexity.
1. Basic Coupon Application

    Description: Apply a single coupon to the entire cart.
    Implemented: Yes
    Details: The coupon provides a fixed percentage or a flat discount on the total cart value.

2. Product-Specific Discount

    Description: Apply a coupon that provides a discount only for specific products.
    Implemented: Yes
    Details: The discount is only applied to eligible products in the cart.

3. Quantity-Based Discount

    Description: Apply a discount based on the quantity of a product in the cart.
    Implemented: Yes
    Details: For example, "Buy 2, get 10% off" or "Buy 3, get the 4th item free."

4. Cart-Level Discount with Minimum Purchase

    Description: Apply a discount to the entire cart if the total value exceeds a certain threshold.
    Implemented: Yes
    Details: The coupon is only valid if the cart value (before discounts) exceeds a specified amount, e.g., "10% off on purchases above $100."

5. Coupon with Expiry Date

    Description: Coupons have a valid date range, after which they expire.
    Implemented: Yes
    Details: The system checks the current date against the coupon’s expiry date before applying the discount.


