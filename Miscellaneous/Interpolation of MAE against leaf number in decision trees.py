#For when I was messing around with interpolation and decision trees, good demonstration of tendency of counter productive oscillation of cubic splines

import numpy as np
import matplotlib.pyplot as plt
from sklearn.tree import DecisionTreeRegressor
from sklearn.metrics import mean_absolute_error
from sklearn.model_selection import train_test_split
from scipy.interpolate import CubicSpline
from scipy.optimize import minimize

# Generate some sample data (replace with your real dataset)
np.random.seed(42)
X = np.random.rand(100, 1) * 10  # Feature
y = np.sin(X).ravel() + np.random.randn(100) * 0.1  # Noisy target

# Split the data into train and test sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# Define a subset of leaf numbers to sample (sparse sampling for interpolation)
leaf_numbers = np.array([2, 10, 50, 100, 200, 500, 1000, 2000, 5000])

# List to store the MAE values for the sampled leaf numbers
mae_values = []

# Loop over the sampled number of leaves
for leaf_num in leaf_numbers:
    model = DecisionTreeRegressor(max_leaf_nodes=leaf_num, random_state=42)
    model.fit(X_train, y_train)
    y_pred = model.predict(X_test)
    mae = mean_absolute_error(y_test, y_pred)
    mae_values.append(mae)

# Interpolate a smooth function using cubic splines
cs = CubicSpline(leaf_numbers, mae_values)

# Define the function for optimization (continuous MAE as a function of leaf_num)
def continuous_mae(leaf_num):
    return cs(leaf_num)

# Use a numerical optimizer to find the minimum of the interpolated function
result = minimize(continuous_mae, x0=500, bounds=[(2, 5000)])

# Get the optimal leaf number and MAE from the result
optimal_leaf_num = result.x[0]
min_mae = result.fun

print(f"Optimal number of leaves (interpolated): {optimal_leaf_num:.2f}")
print(f"Minimum MAE (interpolated): {min_mae:.4f}")

# Plot the interpolation and the sampled points
leaf_range = np.linspace(2, 5000, 500)
plt.figure(figsize=(10, 6))
plt.plot(leaf_range, cs(leaf_range), label='Interpolated MAE (Cubic Spline)', color='b')
plt.scatter(leaf_numbers, mae_values, color='r', label='Sampled Points (MAE)')
plt.axvline(optimal_leaf_num, color='g', linestyle='--', label=f'Optimal Leaves = {optimal_leaf_num:.2f}')
plt.xlabel('Number of Leaves')
plt.ylabel('Mean Absolute Error (MAE)')
plt.title('Interpolated MAE vs Number of Leaves')
plt.legend()
plt.grid(True)
plt.show()
