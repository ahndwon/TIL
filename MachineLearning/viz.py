import numpy as np
from sklearn.datasets import load_iris
from sklearn import tree

iris = load_iris()
'''
# print meta_data
print(iris.feature_names)
print(iris.target_names)

# 데이터 정보
print(iris.data[0])

# 데이터 label , 0: setosa, 1: versicolor, 2: virginica
print(iris.target[0])

# 전체 출력
for i in range(len(iris.target)) : 
	print("Example %d: label %s, feautres %s" % (i, iris.target[i], iris.data[i]))

'''

# first setosa index : 0, first versicolor index : 50, first virginica index :100
test_idx = [0, 50, 100]


'''
테스트 데이터를 훈련 데이터에서 제외함
'''

# training data
train_target = np.delete(iris.target, test_idx)
train_data = np.delete(iris.data, test_idx, axis = 0)

# testing data
test_target = iris.target[test_idx]
test_data = iris.data[test_idx]

clf = tree.DecisionTreeClassifier()
clf.fit(train_data, train_target)

print(test_target)
print(clf.predict(test_data))


# viz code
from sklearn.externals.six import StringIO
import pydot

dot_data = StringIO()
tree.export_graphviz(clf,
	out_file = dot_data,
	feature_names = iris.feature_names,
	class_names = iris.target_names,
	filled = True,
	rounded = True,
	impurity = False)
graph = pydot.graph_from_dot_data(dot_data.getvalue())
graph[0].write_pdf("iris.pdf")

print("test_data[1 ]: ",test_data[1], test_target[1])
print(iris.feature_names, iris.target_names)


