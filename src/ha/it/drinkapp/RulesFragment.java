package ha.it.drinkapp;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

public class RulesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>  {

	private SimpleCursorAdapter cursorAdapter;
	RulesButtonListener listener;
	Button cancelButton, okButton;
	boolean db_init = false;
	Card cardRule;
	
	
	public interface RulesButtonListener {
		public void onCancelSelected();
		public void onOKSelected();
	}
	
	 @Override
	 public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        try {
	        	listener = ((RulesButtonListener) activity);
	        }
	        catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement imageButtonListener");
	        }
	    }  
	    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.rules_fragment, container, false);
	}
	
	@Override
	 public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
		
			registerForContextMenu(getListView());

			if (savedInstanceState == null) {
				loadStandardCardRules();
			}
			else {
				loadListOfAdvices(savedInstanceState);
			}
			
			
			cancelButton 		= (Button) getView().findViewById(R.id.rules_cancel_button);
			okButton 		= (Button) getView().findViewById(R.id.rules_ok_button);
			
			//CANCELBUTTON PRESSED--->
			cancelButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onCancelSelected();
				}
			});
			//OKBUTTON PRESSED--->
			okButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onOKSelected();
				}
			});
			
			this.getLoaderManager().initLoader(0, null, this);

			// Create the adapter
			cursorAdapter = new SimpleCursorAdapter(getActivity(), // Context
					android.R.layout.simple_list_item_2, // List item layout
					null, // Cursor, not available yet
					new String[] {RulesProvider.KEY_CARD_RULE, RulesProvider.KEY_CARD_VALUE}, // Data from the loader 
					new int[] { android.R.id.text1, android.R.id.text2}, // Textview layout(s) for the data
					0); // flags

			getListView().setAdapter(cursorAdapter);
			
	
	}
	public void addAdvice(String advice) {
			
			ContentValues newValue = new ContentValues();			
			newValue.put(RulesProvider.KEY_CARD_RULE, advice);
			newValue.put(RulesProvider.KEY_CARD_VALUE, "hehe");
			
			ContentResolver cr = getActivity().getContentResolver();
			cr.insert(RulesProvider.CONTENT_URI, newValue);
		
	}

	
	private void loadListOfAdvices(Bundle savedInstanceState) {

		getActivity().getSupportLoaderManager().initLoader(0, null, this);

		String[] columns = new String[] { 	RulesProvider.KEY_CARD_RULE, RulesProvider.KEY_CARD_VALUE };
		int[] to = new int[] { R.id.textview_rule, R.id.textview_card };
		
		cursorAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.listview_row, null, columns, to, 0);
		setListAdapter(cursorAdapter);
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
		String[] projection = new String[] { 
				RulesProvider.KEY_ID,
				RulesProvider.KEY_CARD_RULE, 
				RulesProvider.KEY_CARD_VALUE 
		};
		CursorLoader loader = new CursorLoader(
				getActivity(),
				RulesProvider.CONTENT_URI, 
				projection, 
				null, 
				null, 
				null);
		return loader;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getLoaderManager().restartLoader(0, null, this);
	}
	
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		cursorAdapter.swapCursor(cursor);
		//ListView list = (ListView) getView().findViewById(android.R.id.list);
		getListView().setSelection(cursorAdapter.getCount() - 1);
	}

	
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		//Resets cursor
		cursorAdapter.swapCursor(null);
	}
	public RulesButtonListener getListener() {
		return listener;
	}
	public void setListener(RulesButtonListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.rules_activity, menu); //inflate menu

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		//Get a information about selected item
		final AdapterView.AdapterContextMenuInfo info =
				(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		
		switch(item.getItemId()){ 
		case R.id.menu_edit:				//"Edit"  menu item selected
			createEditDialog(info); 		//start edit dialog box
			return true;
		case R.id.menu_delete:	
			new AlertDialog.Builder(getActivity())
				.setTitle("Caution")			//set a title
				.setMessage("Do you really want to delete this item")//Set a message
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,	int which) {
							
							ContentResolver cr = getActivity().getContentResolver();
							cr.delete(RulesProvider.CONTENT_URI,	RulesProvider.KEY_ID + "=?",
									new String[] { String.valueOf(info.id) });
						}
					})
				.setNegativeButton(android.R.string.no, null)//on Cancel action
				.show(); 				//Show dialog	
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	/**
	 * Create a custom dialog box.
	 * Note: seems to be better approach to make a class witch extends a
	 * Dialog class.
	 * On screen orientation change dialog box disappear and text in text
	 * box is not saved :)
	 * @param info AdapterView.AdapterContextMenuInfo
	 */
	private void createEditDialog(final AdapterView.AdapterContextMenuInfo info) {
		
		final ContentResolver cr = getActivity().getContentResolver();
		
		//Get data from DB
		Cursor cursor = cr.query(RulesProvider.CONTENT_URI, null,
				RulesProvider.KEY_ID + "=?",
				new String[] { String.valueOf(info.id) }, null);
		
		while (cursor.moveToNext()) { //if we got some data
			
			//get advice content string
			String message = cursor.getString(cursor.getColumnIndex(RulesProvider.KEY_CARD_RULE));
			
			//create a dialog, note applying style(Theme) in constructor
			final Dialog editDialog = new Dialog(getActivity(), R.style.DialogStyle);
			editDialog.getWindow().setGravity(Gravity.TOP);
			editDialog.setContentView(R.layout.edit_dialog); //custom layout
			editDialog.setTitle(R.string.app_name);		 //title

			//Set functions on buttons and set text to text editor
			final EditText eText = (EditText) editDialog.findViewById(R.id.editText_edit_advice);
			eText.setText(message);

			Button okButton = (Button) editDialog.findViewById(R.id.button_dialog_add);
			okButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					String str = eText.getText().toString();	//new sting

					//Card.changeKnightRule(str);
					ContentValues newValue = new ContentValues();
					newValue.put(RulesProvider.KEY_CARD_RULE, str);

					cr.update(RulesProvider.CONTENT_URI, newValue,
							RulesProvider.KEY_ID + "=?",
							new String[] { String.valueOf(info.id) });
					editDialog.dismiss(); //Close dialog when we done
				}
			});

			Button cancelButton = (Button) editDialog
					.findViewById(R.id.button_dialog_cancel);
			cancelButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					editDialog.dismiss();
				}
			});
			editDialog.show();
		}
	}

	/*
	public void loadStandardRules() {
		
		ContentValues rulesValues1 = new ContentValues();
		ContentValues rulesValues2 = new ContentValues();
		ContentValues rulesValues3 = new ContentValues();
		ContentValues rulesValues4 = new ContentValues();
		
		rulesValues1.put(RulesProvider.KEY_CARD_RULE, card. );
		rulesValues1.put(RulesProvider.KEY_CARD_VALUE, cardValueKnight);
		rulesValues2.put(RulesProvider.KEY_CARD_RULE, ruleQueen);
		rulesValues2.put(RulesProvider.KEY_CARD_VALUE, cardValueQueen); 
		rulesValues3.put(RulesProvider.KEY_CARD_RULE, ruleKing);
		rulesValues3.put(RulesProvider.KEY_CARD_VALUE, cardValueKing); 
		rulesValues4.put(RulesProvider.KEY_CARD_RULE, ruleAce);
		rulesValues4.put(RulesProvider.KEY_CARD_VALUE, cardValueAce); 
		
		ContentResolver cr = getActivity().getContentResolver();
		cr.insert(RulesProvider.CONTENT_URI, rulesValues1);
		cr.insert(RulesProvider.CONTENT_URI, rulesValues2);
		cr.insert(RulesProvider.CONTENT_URI, rulesValues3);
		cr.insert(RulesProvider.CONTENT_URI, rulesValues4);
		
		
		db_init = true;
	}
	*/
	public void currentRules() {
		
		
		//String test = "SELECT KEY_CARD_RULE FROM DATABASE_TABLE WHERE KEY_CARD_VALUE = KNIGHT
		
		
	/*
		ContentResolver cr = getActivity().getContentResolver();
		Cursor cursor = cr.query(RulesProvider.CONTENT_URI, 
									null,
									RulesProvider.KEY_ID + "=?",
				new String[] { String.valueOf(info.id) }, null);
		
		String message = cursor.getString(ge);
		cursor.getColumnIndex(RulesProvider.KEY_CARD_RULE);
		*/
	}
	
	public void loadStandardCardRules() {
		
		ContentValues knightVal = new ContentValues();
		ContentValues queenVal = new ContentValues();
		ContentValues kingVal = new ContentValues();
		ContentValues aceVal = new ContentValues();
				
		
		knightVal.put(RulesProvider.KEY_CARD_RULE, Card.getKnightbackup() );
		knightVal.put(RulesProvider.KEY_CARD_VALUE, "Knight");
		queenVal.put(RulesProvider.KEY_CARD_RULE, Card.getQueenbackup());
		queenVal.put(RulesProvider.KEY_CARD_VALUE, "Queen");
		kingVal.put(RulesProvider.KEY_CARD_RULE, Card.getKingbackup());
		kingVal.put(RulesProvider.KEY_CARD_VALUE, "King");
		aceVal.put(RulesProvider.KEY_CARD_RULE, Card.getAcebackup());
		aceVal.put(RulesProvider.KEY_CARD_VALUE, "Ace");
		
		ContentResolver cr = getActivity().getContentResolver();
		cr.insert(RulesProvider.CONTENT_URI, knightVal);
		cr.insert(RulesProvider.CONTENT_URI, queenVal);
		cr.insert(RulesProvider.CONTENT_URI, kingVal);
		cr.insert(RulesProvider.CONTENT_URI, aceVal);
	}
	
	
}
