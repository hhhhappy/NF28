package TD04;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem.TreeModificationEvent;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TD04Controller {
	
	 private final Image groupIcon = new Image("file:res/group.png");
	 private final Image contactIcon = new Image("file:res/contact.png");
	
	// fxml elements
	@FXML
	TextField lastname;
	@FXML
	TextField firstname;
	@FXML
	TextField nostreet;
	@FXML
	TextField pc;
	@FXML
	TextField city;
	@FXML
	ComboBox<String> country;
	@FXML
	DatePicker birthday;
	@FXML
	RadioButton radiof;
	@FXML
	RadioButton radiom;
	@FXML
	Button buttonvalidate;
	@FXML
	TreeView<Object> treegroup;
	@FXML
	GridPane contactpane;
	@FXML
	Button buttonadd;
	@FXML
	Button buttondelete;
	@FXML
	MenuItem save;
	@FXML
	MenuItem load;
	
	TD04Model model;
	
	//<key of error, error>
	MapChangeListener<String,String> errorListener;	
	
	// List listener
	ListChangeListener<Group> groupListListener = null;
	ListChangeListener<Contact> contactListListener = null;
	
	// current tree item
	TreeItem<Object> currentItem = null;
	// parent of currentItem
	TreeItem<Object> parentItem = null;
	//root of the tree
	TreeItem<Object> rootNode = null;
	
	public TD04Controller() {
		model =  new TD04Model();
	}
	
	public void initialize() {
		model.initialize();
		
		contactpane.visibleProperty().set(false);
		
		// set countries
		country.setItems(Country.getCountryNameList());
		
		// set tree root
		rootNode = new TreeItem<>();
		parentItem = rootNode;	//set parent item
		currentItem = rootNode;	//set current item as root 
		rootNode.setValue("Fiche de contacte");
		rootNode.setExpanded(true);
		treegroup.setRoot(rootNode);
		treegroup.setEditable(true);
		treegroup.setCellFactory(param -> new TextFieldTreeCellImpl());
		
		// set tree click action
		treegroup.getSelectionModel().selectedItemProperty()
				.addListener((obs, oldValue, newValue) -> {
					
					if (newValue.getValue() instanceof String) {
						contactpane.visibleProperty().set(false);
					}
					
					if (newValue.getValue() instanceof Group) {
						contactpane.visibleProperty().set(false);
					}
					
					if (newValue.getValue() instanceof Contact) {
						// set contact value
						model.setContact((Contact)newValue.getValue());
						contactpane.visibleProperty().set(true);
					}
					
					System.out.println("Control: selected = " 
							+ newValue +
							" " + newValue.getClass().getName());
					currentItem = newValue;
					if(currentItem.equals(rootNode)) {
						parentItem = rootNode;
					}
					else {
						parentItem = currentItem.getParent();
					}
				});
		
		//////////////////////////////////////////////
		// Binding VM
		lastname.textProperty().bindBidirectional(model.lastnameProperty());
		firstname.textProperty().bindBidirectional(model.firstnameProperty());
		nostreet.textProperty().bindBidirectional(model.noStreetProperty());
		pc.textProperty().bindBidirectional(model.postCodeProperty());
		city.textProperty().bindBidirectional(model.cityProperty());
		birthday.valueProperty().bindBidirectional(model.birthdayProperty());
		country.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> model.setCountry(newVal));
		radiof.setOnAction(evt -> model.setSex("F"));
		radiom.setOnAction(evt -> model.setSex("M"));
		buttonvalidate.setOnAction(evt -> {
			if (model.validate((Contact)currentItem.getValue())){
				TreeModificationEvent<Object> event = new  TreeModificationEvent<>(TreeItem.valueChangedEvent(), currentItem);
				Event.fireEvent(currentItem, event);
			}
		});
		buttonadd.setOnAction(evt -> model.create(currentItem));
		buttondelete.setOnAction(evt -> model.delete(currentItem));
		save.setOnAction(evt -> model.save());
		load.setOnAction(evt -> {
			currentItem = rootNode;
			parentItem = rootNode;
			FileChooser fc = new FileChooser();
			fc.setTitle("Load File");
			File selectedFile = fc.showOpenDialog(new Stage());
			if (selectedFile != null)
				model.load(selectedFile);
		});
		
		// listeners
		model.sexProperty().addListener((observable, oldVal, newVal) -> {
				radiof.setSelected(newVal.equals("F"));
				radiom.setSelected(newVal.equals("M"));
		});
		model.countryProperty().addListener((observable, oldVal, newVal) -> {
			country.getSelectionModel().select(newVal);
		});
		
		/////////////////////////////////////////////
		// BInding MC
		// save error listener
		errorListener = changed -> {
			if(changed.wasAdded()) {
				printErrorMsg(changed.getKey(), changed.getValueAdded());
			}
			
			if(changed.wasRemoved()) {
				removeErrorMsg(changed.getKey());
			}
		};
		model.errorMsgs.addListener(errorListener);
		
		// contact change listener
		contactListListener = changed ->{
			changed.next();
			if (changed.wasAdded()) {
				changed.getAddedSubList().forEach(item -> addTreeItem(item));
			}
			
			if (changed.wasRemoved()) {
				changed.getRemoved().forEach(item -> removeCurrentTreeItem());
			}
		};
		
		// group change listener
		groupListListener = changed ->{
			changed.next();
			if (changed.wasAdded()) {
				changed.getAddedSubList().forEach(item -> {
						addTreeItem(item);
						item.contacts.addListener(contactListListener);	
						if(item.contacts.size()!=0){
							//when load a file, add all the contact items
							for(Contact c : item.contacts){
								addTreeItem(c, item);
							}
						}
					}
				);
			}
			
			if (changed.wasRemoved()) {
				if(model.groups.size() == 0){
					//resolve the problem witch happened when clear the list of groups
					//treeView wasn't cleared 
					parentItem.getChildren().clear();
				}
				else{
					changed.getRemoved().forEach(item -> removeCurrentTreeItem());
				}
			}
		};
		model.groups.addListener(groupListListener);
	}
	
	private void printErrorMsg(String key, String msg) {
		switch (key) {
		case "street":
			nostreet.setStyle("-fx-border-color: red ;");
			nostreet.setTooltip(new Tooltip(msg));
			break;
			
		case "pc":
			pc.setStyle("-fx-border-color: red ;");
			pc.setTooltip(new Tooltip(msg));
			break;
			
		case "city":
			city.setStyle("-fx-border-color: red ;");
			city.setTooltip(new Tooltip(msg));
			break;
			
		case "firstname":
			firstname.setStyle("-fx-border-color: red ;");
			firstname.setTooltip(new Tooltip(msg));
			break;
			
		case "lastname":
			lastname.setStyle("-fx-border-color: red ;");
			lastname.setTooltip(new Tooltip(msg));
			break;
		}
	}
	
	private void removeErrorMsg(String key) {
		switch (key) {
		case "street":
			nostreet.setStyle(null);
			nostreet.setTooltip(null);
			break;
			
		case "pc":
			pc.setStyle(null);
			pc.setTooltip(null);
			break;
			
		case "city":
			city.setStyle(null);
			city.setTooltip(null);
			break;
			
		case "firstname":
			firstname.setStyle(null);
			firstname.setTooltip(null);
			break;
			
		case "lastname":
			lastname.setStyle(null);
			lastname.setTooltip(null);
			break;
		}
	}
	
	private void addTreeItem(Group grp) {
		TreeItem<Object> grpItem = new TreeItem<Object>(grp, new ImageView(groupIcon));
		currentItem.getChildren().add(grpItem);
		currentItem.setExpanded(true);
	}
	
	private void addTreeItem(Contact contact) {
		TreeItem<Object> contactItem = new TreeItem<Object>(contact, new ImageView(contactIcon));
		currentItem.getChildren().add(contactItem);
	}
	private void addTreeItem(Contact contact, Group grp) {
		//add contact by using his group
		TreeItem<Object> contactItem = new TreeItem<Object>(contact, new ImageView(contactIcon));
		TreeItem<Object> groupItem = null;
		for(TreeItem<Object> g : rootNode.getChildren()){
			if(((Group)g.getValue()).equals(grp)){
				groupItem = g;
				break;
			}
		}
		contact.setGroup(grp);	//set parent group for each contact
		groupItem.getChildren().add(contactItem);
	}
	
	private void removeCurrentTreeItem() {
		parentItem.getChildren().remove(currentItem);
	}
		
    private final class TextFieldTreeCellImpl extends TreeCell<Object> {
    	 
        private TextField textField;
 
        public TextFieldTreeCellImpl() {
        }
 
        @Override
        public void startEdit() {
        	if (!(getTreeItem().getValue() instanceof Group)) {
				return;
			}
        	
            super.startEdit();
            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(((Group) getTreeItem().getValue()).toString());
            setGraphic(getTreeItem().getGraphic());
        }
 
        @Override
        public void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                }
            }
        }
 
        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
 
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                    	((Group)getTreeItem().getValue()).getNameProperty().set(textField.getText());
                    	commitEdit((Group)getTreeItem().getValue());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
	
}
